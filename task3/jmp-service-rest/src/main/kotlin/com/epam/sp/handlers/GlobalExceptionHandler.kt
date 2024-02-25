package com.epam.sp.handlers

import com.epam.sp.services.exceptions.JmpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleJmpExceptionException(e: JmpException, request: WebRequest): ResponseEntity<ErrorInfo> {
        return handleGenericError(e, request, HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler
    fun handleGenericException(e: Exception, request: WebRequest): ResponseEntity<ErrorInfo> {
        return handleGenericError(e, request, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun handleGenericError(e: Throwable, request: WebRequest, status: HttpStatus): ResponseEntity<ErrorInfo> {
        val errorInfo: ErrorInfo = ErrorInfo(Date(), e.message ?: "", request.getDescription(false))
        return ResponseEntity<ErrorInfo>(errorInfo, status)
    }

}


data class ErrorInfo(
    val occurredAt: Date,
    val exception: String,
    val requestDetails: String
)