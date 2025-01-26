package bmstu.nzagainov.gateway.domain

import java.io.Serializable
import java.util.*

data class LibraryResponse(
    val libraryUid: UUID,
    val name: String,
    val address: String,
    val city: String
) : Serializable