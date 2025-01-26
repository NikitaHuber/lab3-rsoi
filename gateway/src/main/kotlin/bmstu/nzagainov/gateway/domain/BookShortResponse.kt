package bmstu.nzagainov.gateway.domain

import java.util.*

data class BookShortResponse(
    val bookUid: UUID,
    val name: String,
    val author: String,
    val genre: String,
)