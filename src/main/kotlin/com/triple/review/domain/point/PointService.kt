package com.triple.review.domain.point

import com.triple.review.domain.review.ReviewService
import com.triple.review.dto.internal.PointEventDto
import com.triple.review.dto.web.PointManipulationResponseDto
import com.triple.review.dto.web.PointRetrieveResponseDto
import com.triple.review.dto.web.PointUpdateRequestDto
import com.triple.review.infrastructure.point.PointHistoryReader
import com.triple.review.infrastructure.point.PointHistoryStore
import com.triple.review.infrastructure.point.PointReader
import com.triple.review.infrastructure.point.PointStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PointService(
    val pointHistoryReader: PointHistoryReader,
    val pointHistoryStore: PointHistoryStore,
    val pointReader: PointReader,
    val pointStore: PointStore,
    val reviewService: ReviewService
) {

    @Transactional
    fun updatePoint(pointUpdateRequestDto: PointUpdateRequestDto): PointManipulationResponseDto {
        val userId = pointUpdateRequestDto.userId
        val reviewId = pointUpdateRequestDto.reviewId

        val pointEvent: PointEventDto = reviewService.calculateChangedPoint(pointUpdateRequestDto)
        reviewService.store(pointUpdateRequestDto)

        if (pointEvent.changedPoint != 0) {
            val initPointHistory = pointUpdateRequestDto.toPointHistoryEntity(pointEvent.changedPoint)
            pointHistoryStore.store(initPointHistory)
        }

        if (pointEvent.bonusPoint != 0) {
            val firstReviewPointHistory = pointUpdateRequestDto.toPointHistoryEntity(pointEvent.bonusPoint)
            pointHistoryStore.store(firstReviewPointHistory)
        }

        val changedPointSum = pointEvent.changedPoint + pointEvent.bonusPoint

        pointStore.update(userId, changedPointSum)

        return PointManipulationResponseDto(userId=userId, reviewId=reviewId, changedPoint=changedPointSum)
    }

    @Transactional
    fun getPointHistory(userId: UUID): PointRetrieveResponseDto {
        val pointHistory = pointHistoryReader.getPointHistory(userId)
        val pointSum = pointReader.getPointSum(userId)
        return PointRetrieveResponseDto.of(pointHistory, pointSum, userId)
    }

    @Transactional
    fun initPointForTest(userId: UUID): UUID {
        val point = pointReader.findByUserId(userId)
        point?.updatePointSumForTest(0)
        return userId
    }
}