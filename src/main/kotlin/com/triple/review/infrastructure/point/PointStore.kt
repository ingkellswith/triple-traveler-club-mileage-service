package com.triple.review.infrastructure.point

import com.triple.review.common.ErrorCode
import com.triple.review.common.exception.InvalidJsonException
import org.springframework.stereotype.Component
import java.util.*

@Component
class PointStore(
    private val pointRepository: PointRepository
){
    fun update(userId: UUID, changedPoint: Int): Int {
        val point = pointRepository.findByUserId(userId) ?: throw InvalidJsonException(ErrorCode.INVALID_JSON)
        return point.updatePointSum(changedPoint)
    }
}