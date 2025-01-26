package bmstu.nzagainov.reservationservice.services

import bmstu.nzagainov.reservationservice.repository.ReservationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ReservationServiceTest {

    val reservationRepository: ReservationRepository = mock(ReservationRepository::class.java)
    val reservationService: ReservationService = ReservationService(reservationRepository)


    @Test
    fun noReservations() {
        `when`(reservationRepository.findAllByUserName("1")).thenReturn(listOf())

        assertThat(reservationService.findAllByUsername("1")).isEmpty()
    }

}