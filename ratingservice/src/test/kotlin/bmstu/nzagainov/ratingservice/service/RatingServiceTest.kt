package bmstu.nzagainov.ratingservice.service

import bmstu.nzagainov.ratingservice.model.Rating
import bmstu.nzagainov.ratingservice.model.RatingResponse
import bmstu.nzagainov.ratingservice.repository.RatingRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class RatingServiceTest {
    val ratingRepository: RatingRepository = mock(RatingRepository::class.java)
    val ratingService: RatingService = RatingService(ratingRepository)

    @Test
    fun getRating() {
        `when`(ratingRepository.findByUserName("1")).thenReturn(Optional.of(Rating("1", 10)))

        assertEquals(RatingResponse(10), ratingService.getRating("1"))
    }
}