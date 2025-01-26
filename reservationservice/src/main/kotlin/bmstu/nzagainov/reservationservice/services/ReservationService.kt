package bmstu.nzagainov.reservationservice.services

import bmstu.nzagainov.reservationservice.model.*
import bmstu.nzagainov.reservationservice.repository.ReservationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
) {

    fun findByStatusAndUserName(status: Status, userName: String) =
        reservationRepository.findAllByStatusAndUsername(status, userName)

    fun findAllByUsername(userName: String) = reservationRepository.findAllByUserName(userName)

    fun addReservation(userName: String, request: ReservationRequest): ReservationResponse {
        val reservation = request.formReservation(userName)
        reservationRepository.save(reservation)
        return reservation.toResponse()
    }


    private fun ReservationRequest.formReservation(userName: String): Reservation =
        Reservation(
            userName = userName,
            bookUid = this.bookUid,
            libraryUid = this.libraryUid,
            status = Status.RENTED,
            startDate = Date(),
            tillDate = this.tillDate
        )

    private fun Reservation.toResponse() =
        ReservationResponse(
            reservationUid = this.reservationUid,
            userName = this.userName,
            bookUid = this.bookUid,
            libraryUid = this.libraryUid,
            status = this.status,
            startDate = this.startDate,
            tillDate = this.tillDate
        )

    fun processReturnRequest(reservationUid: String, date: Date) {
        val reservations = reservationRepository.findAllByReservationUid(UUID.fromString(reservationUid))

        if (reservations.isEmpty())
            throw NoReservationFoundError()

        val reservation = reservations.first()
        reservation.status = if (date > reservation.tillDate) Status.EXPIRED else Status.RETURNED
        reservationRepository.save(reservation)
    }
}