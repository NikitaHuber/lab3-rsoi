package bmstu.nzagainov.ratingservice.repository

import bmstu.nzagainov.ratingservice.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RatingRepository : JpaRepository<Rating, Int> {

    fun findByUserName(userName: String): Optional<Rating>
}