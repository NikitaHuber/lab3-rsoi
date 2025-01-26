package bmstu.nzagainov.gateway.domain

import java.util.*

data class TakeBookRequest(
    val bookUid: UUID,
    val libraryUid: UUID,
    val tillDate: Date?,
)
