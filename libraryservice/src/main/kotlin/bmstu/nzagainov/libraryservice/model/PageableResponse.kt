package bmstu.nzagainov.libraryservice.model

data class PageableResponse<T>(
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val items: List<T>
)