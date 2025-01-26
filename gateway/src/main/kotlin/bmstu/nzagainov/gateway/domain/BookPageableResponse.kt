package bmstu.nzagainov.gateway.domain

data class BookPageableResponse(
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val items: List<BookResponse>
)
