package bmstu.nzagainov.reservationservice.repository

import bmstu.nzagainov.reservationservice.model.Reservation
import bmstu.nzagainov.reservationservice.model.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ReservationRepository : JpaRepository<Reservation, Int> {

    @Query("SELECT r from Reservation r where r.status in (:status) AND r.userName in (:username)")
    fun findAllByStatusAndUsername(status: Status, username: String): List<Reservation>

    fun findAllByUserName(userName: String): List<Reservation>

    fun findAllByReservationUid(reservationUid: UUID): List<Reservation>
}