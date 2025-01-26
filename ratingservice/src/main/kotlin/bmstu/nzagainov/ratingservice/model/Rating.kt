package bmstu.nzagainov.ratingservice.model

import jakarta.persistence.*
import java.util.*

@Entity
class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false)
    var userName: String? = null

    @Column
    var stars: Int? = null

    constructor()

    constructor(userName: String, stars: Int) {
        this.userName = userName
        this.stars = stars
    }

}