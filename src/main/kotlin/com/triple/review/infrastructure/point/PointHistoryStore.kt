package com.triple.review.infrastructure.point

import com.triple.review.domain.point.PointHistory
import org.springframework.stereotype.Component

@Component
class PointHistoryStore(
    private val pointHistoryRepository: PointHistoryRepository
){
    fun store(pointHistory: PointHistory): PointHistory {
        return pointHistoryRepository.save(pointHistory)
    }
}