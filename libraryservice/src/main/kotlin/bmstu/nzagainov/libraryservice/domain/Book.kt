package bmstu.nzagainov.libraryservice.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "book")
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false)
    var bookUid: UUID? = UUID.randomUUID()

    @Column(nullable = false, length = 255)
    var name: String? = null

    @Column(nullable = true, length = 255)
    var author: String? = null

    @Column(nullable = true, length = 255)
    var genre: String? = null

    @Column(nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    var condition: Condition = Condition.EXCELLENT

    constructor()

    constructor(bookUid: UUID, name: String, author: String, genre: String, condition: Condition) {
        this.bookUid = bookUid
        this.name = name
        this.author = author
        this.genre = genre
        this.condition = condition
    }

    constructor(id: Int, bookUid: UUID, name: String, author: String, genre: String, condition: Condition) : this(bookUid, name, author, genre, condition) {
        this.id = id
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (genre?.hashCode() ?: 0)
        result = 31 * result + (condition.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (name != other.name) return false
        if (author != other.author) return false
        if (genre != other.genre) return false
        if (condition != other.condition) return false

        return true
    }
}