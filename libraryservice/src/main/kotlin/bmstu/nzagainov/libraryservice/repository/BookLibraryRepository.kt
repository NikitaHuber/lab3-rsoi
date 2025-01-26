package bmstu.nzagainov.libraryservice.repository

import bmstu.nzagainov.libraryservice.domain.BookLibrary
import bmstu.nzagainov.libraryservice.domain.RelationshipIdClass
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface BookLibraryRepository : JpaRepository<BookLibrary, RelationshipIdClass> {
    fun findAllByLibraryId(libraryId: Int, pageable: Pageable): List<BookLibrary>

    @Query("SELECT bl from BookLibrary bl where bl.book.bookUid in (:bookUid) AND bl.library.libraryUid in (:libraryUid)")
    fun findByBookUidAndLibraryUid(bookUid: UUID, libraryUid: UUID): Optional<BookLibrary>
}