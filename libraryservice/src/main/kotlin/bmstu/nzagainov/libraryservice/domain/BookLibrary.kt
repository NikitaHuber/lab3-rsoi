package bmstu.nzagainov.libraryservice.domain

import jakarta.persistence.*

@Entity
@Table(name = "library_books")
@IdClass(RelationshipIdClass::class)
class BookLibrary {

    @Id
    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "book_id", nullable = false)
    var book: Book? = null

    @Id
    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "library_id", nullable = false)
    var library: Library? = null

    @Column(nullable = false)
    var availableСount: Int? = null

    constructor()

    constructor(book: Book, library: Library, availableCount: Int) {
        this.book = book
        this.library = library
        this.availableСount = availableCount
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookLibrary

        if (book != other.book) return false
        if (library != other.library) return false

        return true
    }

    override fun hashCode(): Int {
        var result = book.hashCode()
        result = 31 * result + (library?.hashCode() ?: 0)
        result = 31 * result + (availableСount?.hashCode() ?: 0)
        return result
    }
}