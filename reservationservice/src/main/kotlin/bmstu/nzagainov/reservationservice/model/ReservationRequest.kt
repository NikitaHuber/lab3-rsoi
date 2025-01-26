package bmstu.nzagainov.reservationservice.model

import java.util.*

data class ReservationRequest(val bookUid: UUID, val libraryUid: UUID, val tillDate: Date)