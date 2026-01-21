package edu.fgiaquintaruiz.ecommerceapi.rest.exception

import edu.fgiaquintaruiz.ecommerceapi.exception.InsufficientStockException
import edu.fgiaquintaruiz.ecommerceapi.exception.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException::class)
    fun handleInsufficientStock(ex: InsufficientStockException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            message = ex.message ?: "Stock error",
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "Resource not found",
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    // English comments: Generic handler for unexpected errors
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "An unexpected error occurred",
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: LocalDateTime
)
