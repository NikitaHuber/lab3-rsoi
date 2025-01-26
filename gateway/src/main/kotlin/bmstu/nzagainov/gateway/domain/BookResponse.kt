package bmstu.nzagainov.gateway.domain

import java.util.*

data class BookResponse(
    val bookUid: UUID,
    val name: String,
    val author: String,
    val genre: String,
    val condition: Condition,
    val availableCount: Int,
)