package com.triple.review.infrastructure.point

import com.triple.review.common.ErrorCode
import com.triple.review.common.exception.NoRecordException
import com.triple.review.domain.point.Point
import org.springframework.stereotype.Component
import java.util.*

@Component
class PointReader(
    private val pointRepository: PointRepository
) {
    fun getPointSum(userId: UUID): Int {
        return pointRepository.findByUserId(userId)?.pointSum ?: throw NoRecordException(ErrorCode.NO_RECORD)
    }

    fun findByUserId(userId: UUID): Point? {
        return pointRepository.findByUserId(userId)
    }
}