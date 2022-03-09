package com.triple.review.domain.point

import com.triple.review.common.ErrorCode
import com.triple.review.common.exception.InvalidJsonException
import com.triple.review.dto.PointRetrieveResponseDto
import com.triple.review.dto.PointUpdateRequestDto
import com.triple.review.infrastructure.point.PointHistoryReader
import com.triple.review.infrastructure.point.PointHistoryStore
import com.triple.review.infrastructure.point.PointReader
import com.triple.review.infrastructure.point.PointStore
import com.triple.review.infrastructure.review.ReviewPhotoReader
import com.triple.review.infrastructure.review.ReviewPhotoStore
import com.triple.review.infrastructure.review.ReviewReader
import com.triple.review.infrastructure.review.ReviewStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PointService(
    val pointHistoryReader: PointHistoryReader,
    val pointHistoryStore: PointHistoryStore,
    val pointReader: PointReader,
    val pointStore: PointStore,
    val reviewPhotoReader: ReviewPhotoReader,
    val reviewPhotoStore: ReviewPhotoStore,
    val reviewReader: ReviewReader,
    val reviewStore: ReviewStore
) {

    @Transactional
    fun updatePoint(pointUpdateRequestDto: PointUpdateRequestDto): UUID {
        val hasPhotoInBeforeReview: Boolean =
            reviewPhotoReader.findIfBeforeReviewHasPhoto(pointUpdateRequestDto.reviewId)
        val hasPhotoInCurrentReview = pointUpdateRequestDto.attachedPhotoIds.isNotEmpty()
        val isFirstReview: Boolean = reviewReader.findIfCurrentReviewIsFirstReview(pointUpdateRequestDto.placeId)

        var changedPoint: Int = when (pointUpdateRequestDto.action) {
            "ADD" -> if (hasPhotoInCurrentReview) 2 else 1
            "MOD" -> if (hasPhotoInBeforeReview && !hasPhotoInCurrentReview) -1
            else if (!hasPhotoInBeforeReview && hasPhotoInCurrentReview) 1
            else 0
            "DELETE" -> 0 - pointHistoryReader.findPointSumToDelete(pointUpdateRequestDto.reviewId)
            else -> {
                throw InvalidJsonException(ErrorCode.INVALID_JSON)
            }
        }

        val initPointHistory = pointUpdateRequestDto.toPointHistoryEntity(changedPoint)
        if (changedPoint != 0) {
            pointHistoryStore.store(initPointHistory)
        }
        if (isFirstReview) {
            val firstReviewPointHistory = pointUpdateRequestDto.toPointHistoryEntity(1)
            pointHistoryStore.store(firstReviewPointHistory)
            changedPoint += 1
        }

        when (pointUpdateRequestDto.action) {
            "ADD" -> {
                reviewStore.store(pointUpdateRequestDto.toReviewEntity())
                reviewPhotoStore.store(pointUpdateRequestDto.toReviewPhotoEntityList())
            }
            "MOD" -> {
                if (reviewReader.findUserIdByReviewId(pointUpdateRequestDto.reviewId) != pointUpdateRequestDto.userId) {
                    throw InvalidJsonException(ErrorCode.INVALID_USER)
                }
                reviewStore.update(pointUpdateRequestDto.reviewId, pointUpdateRequestDto.content)
                reviewPhotoStore.reviewPhotoUpdate(
                    pointUpdateRequestDto.reviewId,
                    pointUpdateRequestDto.attachedPhotoIds
                )
            }
            "DELETE" -> {
                if (reviewReader.findUserIdByReviewId(pointUpdateRequestDto.reviewId) != pointUpdateRequestDto.userId) {
                    throw InvalidJsonException(ErrorCode.INVALID_USER)
                }
                reviewStore.delete(pointUpdateRequestDto.reviewId)
                reviewPhotoStore.delete(pointUpdateRequestDto.reviewId)
            }
            else -> {
                throw InvalidJsonException(ErrorCode.INVALID_JSON)
            }
        }

        pointStore.update(pointUpdateRequestDto.userId, changedPoint)

        return initPointHistory.serviceId
    }

    @Transactional
    fun getPointHistory(userId: UUID): PointRetrieveResponseDto {
        val pointHistory = pointHistoryReader.getPointHistory(userId)
        val pointSum = pointReader.getPointSum(userId)
        return PointRetrieveResponseDto.of(pointHistory, pointSum, userId)
    }

}