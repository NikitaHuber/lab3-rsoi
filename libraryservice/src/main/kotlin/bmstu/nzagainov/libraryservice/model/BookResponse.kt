package bmstu.nzagainov.libraryservice.model

import java.util.UUID

data class BookResponse(
    val bookUid: UUID,
    val name: String,
    val author: String,
    val genre: String,
    val condition: String,
    val availableCount: Int,
)