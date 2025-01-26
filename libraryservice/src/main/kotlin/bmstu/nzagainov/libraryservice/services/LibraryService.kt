package bmstu.nzagainov.libraryservice.services

import bmstu.nzagainov.libraryservice.domain.Book
import bmstu.nzagainov.libraryservice.domain.BookLibrary
import bmstu.nzagainov.libraryservice.domain.Condition
import bmstu.nzagainov.libraryservice.domain.Library
import bmstu.nzagainov.libraryservice.model.BookResponse
import bmstu.nzagainov.libraryservice.model.PageableResponse
import bmstu.nzagainov.libraryservice.model.LibraryResponse
import bmstu.nzagainov.libraryservice.repository.BookLibraryRepository
import bmstu.nzagainov.libraryservice.repository.BookRepository
import bmstu.nzagainov.libraryservice.repository.LibraryRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*


@Service
class LibraryService(
    private val libraryRepository: LibraryRepository,
    private val bookRepository: BookRepository,
    private val bookLibraryRepository: BookLibraryRepository,
) {

    @Transactional
    fun getLibrariesByCity(city: String, pageRequest: PageRequest): PageableResponse<LibraryResponse> {
        val libraries = libraryRepository.findAllByCity(city, pageRequest).map { it.toResponse() }
        return PageableResponse(
            page = pageRequest.pageNumber,
            pageSize = pageRequest.pageSize,
            totalElements = libraries.size,
            items = libraries
        )
    }


    @Transactional
    fun getLibraryBooks(libraryUUID: UUID, pageRequest: PageRequest, showAll: Boolean) : PageableResponse<BookResponse> {
        val libraryId = libraryRepository.findAllByLibraryUid(libraryUUID).first().id
        val books = bookLibraryRepository.findAllByLibraryId(libraryId!!, pageRequest)
            .filter { showAll || it.availableСount!! > 0}
            .map {
                it.book!!.toResponse(it.availableСount!!)
            }
        return PageableResponse(
            page = pageRequest.pageNumber,
            pageSize = pageRequest.pageSize,
            totalElements = books.size,
            items = books
        )
    }

    @Transactional
    fun changeBookCondition(bookUid: String, condition: Condition) {
        val book = bookRepository.findByBookUid(UUID.fromString(bookUid))
            .orElseThrow { throw EntityNotFoundException("Book $bookUid not found") }

        book.condition = condition
        bookRepository.save(book)
    }

    @Transactional
    fun changeAvailableCount(libraryUid: String, bookUid: String, availableCount: Int) {
        val entity: BookLibrary = bookLibraryRepository
            .findByBookUidAndLibraryUid(UUID.fromString(bookUid), UUID.fromString(libraryUid))
            .orElseThrow {
                throw EntityNotFoundException("Book $bookUid not found")
            }
        entity.availableСount = entity.availableСount!! + availableCount
        bookLibraryRepository.save(entity)
    }

    @Transactional
    fun getLibrary(libraryUUID: String): LibraryResponse {
        return libraryRepository.findAllByLibraryUid(UUID.fromString(libraryUUID)).first().toResponse()
    }

    @Transactional
    fun getBook(bookUid: String): BookResponse {
        return bookRepository.findByBookUid(UUID.fromString(bookUid))
            .orElseThrow { throw EntityNotFoundException("Book $bookUid not found") }
            .toResponse(0)
    }

    private fun Library.toResponse() = LibraryResponse(
        libraryUid!!,
        name!!,
        address!!,
        city!!
    )

    private fun Book.toResponse(availableCount: Int) = BookResponse(
        bookUid!!,
        name!!,
        author!!,
        genre!!,
        condition.name,
        availableCount
    )
}