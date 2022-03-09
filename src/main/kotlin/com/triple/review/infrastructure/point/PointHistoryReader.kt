package com.triple.review.infrastructure.point

import com.triple.review.domain.point.PointHistory
import org.springframework.stereotype.Component
import java.util.*

@Component
class PointHistoryReader(
    private val pointHistoryRepository: PointHistoryRepository
) {
    fun getPointHistory(userId: UUID): List<PointHistory> {
        return pointHistoryRepository.findByUserId(userId)
    }

    fun findPointSumToDelete(reviewId: UUID): Int {
        return pointHistoryRepository.findPointSumByReviewId(reviewId)
    }
}