package bmstu.nzagainov.reservationservice.model

import jakarta.persistence.*
import java.util.*

data class ReservationResponse(
    var reservationUid: UUID? = UUID.randomUUID(),
    var userName: String? = null,
    var bookUid: UUID? = UUID.randomUUID(),
    var libraryUid: UUID? = UUID.randomUUID(),
    var status: Status = Status.RENTED,
    var startDate: Date? = null,
    var tillDate: Date? = null
)