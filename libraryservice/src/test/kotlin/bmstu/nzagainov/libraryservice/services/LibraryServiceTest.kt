package bmstu.nzagainov.libraryservice.services

import bmstu.nzagainov.libraryservice.domain.Book
import bmstu.nzagainov.libraryservice.domain.Condition
import bmstu.nzagainov.libraryservice.model.BookResponse
import bmstu.nzagainov.libraryservice.repository.BookLibraryRepository
import bmstu.nzagainov.libraryservice.repository.BookRepository
import bmstu.nzagainov.libraryservice.repository.LibraryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
class LibraryServiceTest {
    private val libraryRepository: LibraryRepository = mock(LibraryRepository::class.java)
    private val bookRepository: BookRepository = mock(BookRepository::class.java)
    private val bookLibraryRepository: BookLibraryRepository = mock(BookLibraryRepository::class.java)
    private val libraryService = LibraryService(libraryRepository, bookRepository, bookLibraryRepository)

    @Test
    fun findAll() {
        val book = Book(
            bookUid = UUID.randomUUID(),
            name = "kNigga",
            author = "Chel",
            genre = "Fantasy",
            condition = Condition.EXCELLENT
        )
        `when`(bookRepository.findByBookUid(book.bookUid!!)).thenReturn(Optional.of(book))

        assertEquals(libraryService.getBook(bookUid = book.bookUid.toString()), BookResponse(
            bookUid = book.bookUid!!,
            name = book.name!!,
            author = book.author!!,
            genre = book.genre!!,
            condition = book.condition.name,
            availableCount = 0
        ))
    }
}