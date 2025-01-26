package bmstu.nzagainov.libraryservice.model

import java.util.*

data class LibraryResponse(
    val libraryUid: UUID,
    val name: String,
    val address: String,
    val city: String
)