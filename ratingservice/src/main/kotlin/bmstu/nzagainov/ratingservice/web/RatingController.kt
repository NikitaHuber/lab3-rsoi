package bmstu.nzagainov.ratingservice.web

import bmstu.nzagainov.ratingservice.service.RatingService
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class RatingController(
    private val ratingService: RatingService
) {

    @GetMapping("/rating")
    fun getRating(
        @RequestParam user: String,
    ) = ratingService.getRating(user)

    @PostMapping("/rating")
    @Modifying
    fun addRating(
        @RequestParam user: String,
    ): Unit {
        ratingService.addRating(user)
    }

    @PutMapping("/rating")
    fun updateRating(
        @RequestParam user: String,
        @RequestParam diff: Int
    ) = ratingService.updateRating(user, diff)
}