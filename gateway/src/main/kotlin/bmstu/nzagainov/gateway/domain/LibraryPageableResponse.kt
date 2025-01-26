package bmstu.nzagainov.gateway.domain

import java.io.Serializable

data class LibraryPageableResponse(
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val items: List<LibraryResponse>
): Serializable
