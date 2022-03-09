package com.triple.review.common

import com.triple.review.common.exception.InvalidJsonException
import com.triple.review.common.exception.NoRecordException
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.sql.SQLException

@ControllerAdvice
class CommonControllerAdvice {


    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun internalServerException(e: Exception): ResponseEntity<CommonResponse> {
        return ResponseEntity.internalServerError().body(CommonResponse(ErrorCode.INTERNAL_SERVER_ERROR.errorMsg))
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [NoRecordException::class])
    fun noRecordException(e: Exception): ResponseEntity<CommonResponse> {
        return ResponseEntity.internalServerError().body(CommonResponse(ErrorCode.INTERNAL_SERVER_ERROR.errorMsg))
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [InvalidJsonException::class])
    fun invalidJsonException(e: InvalidJsonException): ResponseEntity<CommonResponse> {
        return ResponseEntity.badRequest().body(CommonResponse(ErrorCode.INVALID_JSON.errorMsg))
    }
}