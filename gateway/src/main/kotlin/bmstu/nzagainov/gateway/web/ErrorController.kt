package bmstu.nzagainov.gateway.web

import bmstu.nzagainov.gateway.domain.CBException
import bmstu.nzagainov.gateway.domain.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ErrorController {

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(CBException::class)
    fun unavailable(exception: CBException): ErrorResponse {
        return ErrorResponse(exception.message)
    }
}