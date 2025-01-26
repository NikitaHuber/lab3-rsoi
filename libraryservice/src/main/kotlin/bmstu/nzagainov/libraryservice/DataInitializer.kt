package bmstu.nzagainov.libraryservice

import bmstu.nzagainov.libraryservice.domain.Book
import bmstu.nzagainov.libraryservice.domain.BookLibrary
import bmstu.nzagainov.libraryservice.domain.Condition
import bmstu.nzagainov.libraryservice.domain.Library
import bmstu.nzagainov.libraryservice.repository.BookLibraryRepository
import bmstu.nzagainov.libraryservice.repository.BookRepository
import bmstu.nzagainov.libraryservice.repository.LibraryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataInitializer : CommandLineRunner {

    @Autowired
    private lateinit var libraryRepository: LibraryRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var bookLibraryRepository: BookLibraryRepository

    @Transactional
    override fun run(vararg args: String?) {
        val library by lazy {
            Library(
                libraryUid = UUID.fromString("83575e12-7ce0-48ee-9931-51919ff3c9ee"),
                name = "Библиотека имени 7 Непьющих",
                city = "Москва",
                address = "2-я Бауманская ул., д.5, стр.1"
            )
        }
        val book by lazy {
            Book(
                bookUid = UUID.fromString("f7cdc58f-2caf-4b15-9727-f89dcc629b27"),
                name = "Краткий курс C++ в 7 томах",
                author = "Бьерн Страуструп",
                genre = "Научная фантастика",
                condition = Condition.EXCELLENT
            )
        }
        val bookLibrary by lazy { BookLibrary(book, library, 1) }

        if (!bookRepository.findById(1).isPresent) {
            bookRepository.save(book)
            libraryRepository.save(library)
            bookLibraryRepository.save(bookLibrary)
        }
    }
}