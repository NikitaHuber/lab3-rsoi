package bmstu.nzagainov.libraryservice.repository

import bmstu.nzagainov.libraryservice.domain.Library
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LibraryRepository : JpaRepository<Library, Int> {
    fun findAllByCity(city: String, pageable: Pageable): List<Library>

    fun findAllByLibraryUid(libraryUid: UUID): List<Library>
}