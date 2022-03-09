package com.triple.review.infrastructure.point

import org.springframework.stereotype.Component
import java.util.*

@Component
class PointStore(
    private val pointRepository: PointRepository
){
    fun update(userId: UUID, changedPoint: Int): Int {
        val point = pointRepository.findByUserId(userId)
        return point.updatePointSum(changedPoint)
    }
}