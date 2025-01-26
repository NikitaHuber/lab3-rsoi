package bmstu.nzagainov.libraryservice.domain

import java.io.Serializable

class RelationshipIdClass : Serializable {
    lateinit var book: Book
    lateinit var library: Library
}