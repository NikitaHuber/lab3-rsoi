package bmstu.nzagainov.gateway.domain

import java.util.*

data class TakeBookResponse(
    val reservationUid: UUID?,
    val status: Status,
    val startDate: String,
    val tillDate: String,
    val book: BookShortResponse,
    val library: LibraryResponse,
    val rating: RatingResponse,
)
