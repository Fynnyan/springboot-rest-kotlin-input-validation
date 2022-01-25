package com.fynnian.kotlininputvalidation.error

import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.ZonedDateTime
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class RestExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun handleValidationErrors(exception: MethodArgumentNotValidException): ValidationErrorResponse {
    val errors: Map<String, String> = exception.bindingResult.allErrors.associate {
      if (it is FieldError) {
        it.field to (it.defaultMessage ?: "")
      } else "global" to (it?.defaultMessage ?: "")
    }
    return ValidationErrorResponse(message = "The validation for the request failed", errors = errors)
  }
  @ExceptionHandler(ConstraintViolationException::class)
  fun handleConstraintValidation(exception: ConstraintViolationException): ValidationErrorResponse {
    val errors = exception.constraintViolations.associate {
      it.propertyPath.toString() to it.message
    }
    return ValidationErrorResponse(message = "The validation for the request failed", errors = errors)
  }
}

data class ValidationErrorResponse(
    val timestamp: ZonedDateTime = ZonedDateTime.now(),
    val status: Int = 400,
    val code: String = "Bad Request",
    val message: String,
    val errors: Map<String, String> = emptyMap()
)