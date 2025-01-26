package bmstu.nzagainov.libraryservice.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "library")
class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false)
    var libraryUid: UUID? = UUID.randomUUID()

    @Column(nullable = false, length = 80)
    var name: String? = null

    @Column(nullable = false, length = 255)
    var city: String? = null

    @Column(nullable = false, length = 255)
    var address: String? = null

    constructor()

    constructor(libraryUid: UUID, name: String, city: String, address: String) {
        this.libraryUid = libraryUid
        this.name = name
        this.city = city
        this.address = address
    }

    constructor(id: Int, libraryUid: UUID, name: String, city: String, address: String) : this(libraryUid, name, city, address) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Library

        if (name != other.name) return false
        if (city != other.city) return false
        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        return result
    }
}