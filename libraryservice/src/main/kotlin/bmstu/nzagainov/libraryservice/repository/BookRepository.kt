package bmstu.nzagainov.libraryservice.repository

import bmstu.nzagainov.libraryservice.domain.Book
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, Int> {
    fun findByBookUid(bookUid: UUID): Optional<Book>
}