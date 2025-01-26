package bmstu.nzagainov.gateway.domain

import java.util.*

data class ReturnBookRequest(
    val date: Date,
    val condition: Condition
)