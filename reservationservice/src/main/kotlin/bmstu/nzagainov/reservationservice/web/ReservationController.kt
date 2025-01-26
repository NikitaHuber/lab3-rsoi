package bmstu.nzagainov.reservationservice.web

import bmstu.nzagainov.reservationservice.model.Reservation
import bmstu.nzagainov.reservationservice.model.ReservationRequest
import bmstu.nzagainov.reservationservice.model.ReturnBookRequest
import bmstu.nzagainov.reservationservice.model.Status
import bmstu.nzagainov.reservationservice.services.ReservationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ReservationController(
    private val reservationService: ReservationService,
) {

    @GetMapping("/users/{userName}/reservations")
    fun getReservations(
        @PathVariable userName: String,
        @RequestParam(required = false) status: Status?
    ): List<Reservation> {
        return if (status != null)
            reservationService.findByStatusAndUserName(status, userName)
        else
            reservationService.findAllByUsername(userName)
    }

    @PostMapping("/users/{userName}/reservations")
    fun createReservation(
        @PathVariable userName: String,
        @RequestBody request: ReservationRequest
    ) = reservationService.addReservation(userName, request)

    @PostMapping("/reservations/{reservationUid}/return")
    fun returnReservation(
        @PathVariable reservationUid: String,
        @RequestBody request: ReturnBookRequest,
    ) = reservationService.processReturnRequest(reservationUid, request.date)
}