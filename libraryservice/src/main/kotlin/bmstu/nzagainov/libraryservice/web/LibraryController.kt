package bmstu.nzagainov.libraryservice.web

import bmstu.nzagainov.libraryservice.domain.Condition
import bmstu.nzagainov.libraryservice.services.LibraryService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/")
class LibraryController(private val libraryService: LibraryService) {

    @GetMapping("libraries")
    fun getLibrariesByCity(
        @RequestParam city: String,
        pageable: Pageable,
    ) = libraryService.getLibrariesByCity(city, PageRequest.of(pageable.pageNumber, pageable.pageSize))

    @GetMapping("libraries/{libraryUid}")
    fun getLibrary(@PathVariable libraryUid: String) = libraryService.getLibrary(libraryUid)

    @GetMapping("books/{bookUid}")
    fun getLibraryBooks(
        @PathVariable bookUid: String,
    ) = libraryService.getBook(bookUid)

    @GetMapping("libraries/{libraryUid}/books")
    fun getLibraryBooks(
        @PathVariable libraryUid: String,
        @RequestParam(defaultValue = "false") showAll: Boolean,
        pageable: Pageable
    ) = libraryService.getLibraryBooks(UUID.fromString(libraryUid), PageRequest.of(pageable.pageNumber, pageable.pageSize), showAll)

    @PutMapping("libraries/{libraryUid}/books/{bookUid}")
    fun updateBook(
        @PathVariable libraryUid: String,
        @PathVariable bookUid: String,
        @RequestParam(required = false) condition: Condition?,
        @RequestParam(required = false) availableCountDiff: Int?,
    ) {
        condition?.let { libraryService.changeBookCondition(bookUid, condition) }
        availableCountDiff?.let { libraryService.changeAvailableCount(libraryUid, bookUid, it) }
    }
}