package com.triple.review.infrastructure.point

import com.triple.review.common.ErrorCode
import com.triple.review.common.exception.InvalidJsonException
import org.springframework.stereotype.Component
import java.util.*

@Component
class PointReader(
    private val pointRepository: PointRepository
) {
    fun getPointSum(userId: UUID): Int {
        return pointRepository.findByUserId(userId)?.pointSum ?: throw InvalidJsonException(ErrorCode.INVALID_JSON)
    }
}