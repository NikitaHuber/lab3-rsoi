package bmstu.nzagainov.ratingservice.service

import bmstu.nzagainov.ratingservice.model.Rating
import bmstu.nzagainov.ratingservice.model.RatingResponse
import bmstu.nzagainov.ratingservice.repository.RatingRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class RatingService(
    private val ratingRepository: RatingRepository
) {
    fun getRating(userName: String) = getOrCreateRating(userName).toResponse()

    fun updateRating(userName: String, diff: Int) {
        val rating = getOrCreateRating(userName)

        if (rating.stars!! + diff > 100)
            rating.stars = 100
        else if (rating.stars!! + diff < 1)
            rating.stars = 1
        else
            rating.stars = rating.stars!! + diff
        ratingRepository.save(rating)
    }

    fun addRating(user: String) {
        ratingRepository.save(Rating(userName = user, stars = 1))
    }

    private fun getOrCreateRating(user: String): Rating {
        val entity = ratingRepository.findByUserName(user)
        return if (entity.isPresent) {
            entity.get()
        } else {
            ratingRepository.save(getInitialRating(user))
        }
    }

    private fun getInitialRating(user: String) = Rating(userName = user, stars = 75)

    private fun Rating.toResponse() = RatingResponse(stars = this.stars!!)
}