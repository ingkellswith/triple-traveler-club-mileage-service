package com.triple.review.infrastructure.point

import org.springframework.stereotype.Component
import java.util.*

@Component
class PointReader(
    private val pointRepository: PointRepository
) {
    fun getPointSum(userId: UUID): Int {
        return pointRepository.findByUserId(userId)?.pointSum
    }
}