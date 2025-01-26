package bmstu.nzagainov.gateway.domain

import java.util.*

data class BookReservationResponse(
    var reservationUid: UUID? = UUID.randomUUID(),
    var status: Status = Status.RENTED,
    var startDate: Date? = null,
    var tillDate: Date? = null,

    var book: BookShortResponse? = null,
    var library: LibraryResponse? = null,
)
