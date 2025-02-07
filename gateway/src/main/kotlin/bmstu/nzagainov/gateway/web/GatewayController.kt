package bmstu.nzagainov.gateway.web

import bmstu.nzagainov.gateway.PathResolver
import bmstu.nzagainov.gateway.domain.*
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat
import java.util.*


@RestController
@RequestMapping("/api/v1/")
class GatewayController(
    private val pathResolver: PathResolver,
    circuitBreakerRegistry: CircuitBreakerRegistry,
) {

    private val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("backendA")
    private val restTemplate = RestTemplate()

    private val dateApiFormat = SimpleDateFormat("yyyy-MM-dd")

    @GetMapping("/libraries")
    fun getLibraries(
        @RequestParam city: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ) = responseGetLibraries(city, page, size)

    @GetMapping("libraries/{libraryUid}/books")
    fun getBooks(
        @PathVariable libraryUid: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "false") showAll: Boolean
    ) = responseGetBooks(libraryUid, page, size, showAll)

    @GetMapping("reservations")
    fun getReservations(
        @RequestHeader("X-User-Name") userName: String,
    ) = responseGetReservations(userName)

    @PostMapping("reservations")
    fun takeBook(
        @RequestHeader("X-User-Name") userName: String,
        @RequestBody request: TakeBookRequest,
    ): TakeBookResponse {
        val book = getBookShortResponse(request.bookUid, false)
        val library = getLibrary(request.libraryUid, false)

        val rating = responseGetRating(userName)
        val rentedSize = restTemplate.getForObject(
            "${pathResolver.reservation}/users/$userName/reservations?status=RENTED",
            Array<Reservation>::class.java
        )!!.size

        if (rating!!.stars <= rentedSize) {
            throw Error("Allowable count: ${rating.stars}, taken: $rentedSize")
        }

        val reservation = restTemplate.postForObject(
            "${pathResolver.reservation}/users/$userName/reservations",
            request,
            Reservation::class.java
        )
        return TakeBookResponse(
            reservationUid = reservation!!.reservationUid,
            status = reservation.status,
            startDate = dateApiFormat.format(reservation.startDate!!),
            tillDate = dateApiFormat.format(reservation.tillDate!!),
            book = book,
            library = library!!,
            rating = rating,
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/reservations/{reservationUid}/return")
    fun returnBook(
        @RequestHeader("X-User-Name") userName: String,
        @PathVariable reservationUid: String,
        @RequestBody request: ReturnBookRequest,
    ) {
        val reservation = restTemplate.getForObject(
            "${pathResolver.reservation}/users/$userName/reservations?status=RENTED",
            Array<Reservation>::class.java
        )!!.first { it.reservationUid == UUID.fromString(reservationUid) }

        val bookCondition = restTemplate.getForObject(
            "${pathResolver.library}/books/${reservation.bookUid}",
            BookResponse::class.java
        )!!.condition

        restTemplate.postForObject(
            "${pathResolver.reservation}/reservations/$reservationUid/return",
            ReservationReturnBookRequest(date = request.date),
            Unit.javaClass
        )

        var diff = 0
        if (bookCondition.ordinal < request.condition.ordinal) diff -= 10
        if (reservation.tillDate!! < request.date) diff -= 10
        if (diff == 0) diff += 1
        requestStarsDiff(diff, userName)
    }

    @GetMapping("/rating")
    fun getRating(
        @RequestHeader("X-User-Name") userName: String
    ) = responseGetRating(userName)

    private fun responseGetLibraries(city: String, page: Int, size: Int) = try {
        circuitBreaker.executeSupplier {
            restTemplate.getForObject(
                "${pathResolver.library}libraries?city=$city&page=$page&size=$size",
                LibraryPageableResponse::class.java
            )!!
        }
    } catch (e: Exception) {
        throw CBException(e.message)
    }

    private fun responseGetBooks(
        libraryUid: String,
        page: Int,
        size: Int,
        showAll: Boolean
    ) = try {
        circuitBreaker.executeSupplier {
            restTemplate.getForObject(
                "${pathResolver.library}libraries/${libraryUid}/books?showAll=$showAll&page=$page&size=$size",
                BookPageableResponse::class.java
            )!!
        }
    } catch (e: Exception) {
        throw CBException(e.message)
    }

    private fun responseGetReservations(userName: String): List<BookReservationResponse> = try {
        circuitBreaker.executeSupplier {
            restTemplate.getForObject(
                "${pathResolver.reservation}/users/$userName/reservations",
                Array<Reservation>::class.java
            )!!.map { BookReservationResponse(
                reservationUid = it.reservationUid,
                status = it.status,
                startDate = it.startDate,
                tillDate = it.tillDate,
                book = getBookShortResponse(it.bookUid!!),
                library = getLibrary(it.libraryUid!!)
            ) }
        }
    } catch (e: Exception) {
        throw CBException(e.message)
    }




    private fun responseGetRating(userName: String) = try {
        circuitBreaker.executeSupplier {
            restTemplate.getForObject(
                "${pathResolver.rating}/rating?user=$userName",
                RatingResponse::class.java
            )!!
        }
    } catch (e: Exception) {
        throw CBException("Bonus Service unavailable")
    }

    private fun requestStarsDiff(diff: Int, userName: String) {
        restTemplate.put(
            "${pathResolver.rating}/rating?user=$userName&diff=$diff",
            String::class.java,
        )
    }

    private fun getBookShortResponse(bookUid: UUID, fallback: Boolean = true) = try {
        circuitBreaker.executeSupplier {
            restTemplate.getForObject(
                "${pathResolver.library}/books/$bookUid",
                BookShortResponse::class.java
            )
        }
    } catch (e: Exception) {
        if (!fallback) throw CBException(e.message)
        BookShortResponse(
            bookUid = bookUid,
            name = "",
            author = "",
            genre = "",
        )
    }


    private fun getLibrary(libraryUid: UUID, fallback: Boolean = true) = try {
        restTemplate.getForObject(
            "${pathResolver.library}/libraries/$libraryUid",
            LibraryResponse::class.java
        )
    } catch (e: Exception) {
        if (!fallback) throw CBException(e.message)
        LibraryResponse(
            libraryUid = libraryUid,
            name = "",
            address = "",
            city = ""
        )
    }
}