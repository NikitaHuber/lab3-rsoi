package bmstu.nzagainov.gateway.domain

import java.util.*

data class Reservation (
    var id: Int? = null,
    var reservationUid: UUID? = UUID.randomUUID(),
    var userName: String? = null,
    var bookUid: UUID? = UUID.randomUUID(),
    var libraryUid: UUID? = UUID.randomUUID(),
    var status: Status = Status.RENTED,
    var startDate: Date? = null,
    var tillDate: Date? = null
)