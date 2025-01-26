package bmstu.nzagainov.reservationservice.model

import jakarta.persistence.*
import java.util.*

@Entity
class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false)
    var reservationUid: UUID? = UUID.randomUUID()

    @Column(nullable = false)
    var userName: String? = null

    @Column(nullable = false)
    var bookUid: UUID? = UUID.randomUUID()

    @Column(nullable = false)
    var libraryUid: UUID? = UUID.randomUUID()

    @Column(nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    var status: Status = Status.RENTED

    @Column(nullable = false)
    var startDate: Date? = null

    @Column(nullable = false)
    var tillDate: Date? = null

    constructor()

    constructor(userName: String, bookUid: UUID, libraryUid: UUID, status: Status, startDate: Date, tillDate: Date) {
        this.userName = userName
        this.bookUid = bookUid
        this.libraryUid = libraryUid
        this.status = status
        this.startDate = startDate
        this.tillDate = tillDate
    }
}