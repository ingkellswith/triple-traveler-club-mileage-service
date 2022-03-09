package com.triple.review.domain.review

import com.triple.review.common.ErrorCode
import com.triple.review.common.exception.InvalidJsonException
import com.triple.review.domain.point.PointService
import com.triple.review.dto.internal.PointEventDto
import com.triple.review.dto.web.PointUpdateRequestDto
import com.triple.review.infrastructure.point.PointHistoryReader
import com.triple.review.infrastructure.review.ReviewPhotoReader
import com.triple.review.infrastructure.review.ReviewPhotoStore
import com.triple.review.infrastructure.review.ReviewReader
import com.triple.review.infrastructure.review.ReviewStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ReviewService(
    val reviewPhotoReader: ReviewPhotoReader,
    val reviewPhotoStore: ReviewPhotoStore,
    val reviewReader: ReviewReader,
    val reviewStore: ReviewStore,
    val pointHistoryReader: PointHistoryReader
) {
    @Transactional
    fun calculateChangedPoint(pointUpdateRequestDto: PointUpdateRequestDto): PointEventDto {
        val reviewId = pointUpdateRequestDto.reviewId
        val attachedPhotoIds = pointUpdateRequestDto.attachedPhotoIds
        val placeId = pointUpdateRequestDto.placeId

        val hasPhotoInBeforeReview = reviewPhotoReader.findIfBeforeReviewHasPhoto(reviewId)
        val hasPhotoInCurrentReview = attachedPhotoIds.isNotEmpty()
        val isFirstReview = reviewReader.findIfCurrentReviewIsFirstReview(placeId)
        var bonusPoint: Int = 0

        if (isFirstReview) {
            bonusPoint = 1
        }

        val changedPoint: Int = when (pointUpdateRequestDto.action) {
            "ADD" -> {
                if (hasPhotoInCurrentReview) 2 else 1
            }
            "MOD" -> {
                if (hasPhotoInBeforeReview && !hasPhotoInCurrentReview) -1
                else if (!hasPhotoInBeforeReview && hasPhotoInCurrentReview) 1
                else 0
            }
            "DELETE" -> {
                0 - pointHistoryReader.findPointSumToDelete(reviewId)
            }
            else -> {
                throw InvalidJsonException(ErrorCode.INVALID_JSON)
            }
        }

        return PointEventDto(changedPoint, bonusPoint)
    }

    @Transactional
    fun store(pointUpdateRequestDto: PointUpdateRequestDto): UUID {
        when (pointUpdateRequestDto.action) {
            "ADD" -> {
                reviewStore(
                    pointUpdateRequestDto.toReviewEntity(),
                    pointUpdateRequestDto.toReviewPhotoEntityList()
                )
            }
            "MOD" -> {
                reviewUpdate(
                    pointUpdateRequestDto.reviewId,
                    pointUpdateRequestDto.userId,
                    pointUpdateRequestDto.content,
                    pointUpdateRequestDto.attachedPhotoIds
                )
            }
            "DELETE" -> {
                reviewDelete(
                    pointUpdateRequestDto.reviewId,
                    pointUpdateRequestDto.userId
                )
            }
            else -> {
                throw InvalidJsonException(ErrorCode.INVALID_JSON)
            }
        }

        return pointUpdateRequestDto.reviewId
    }

    @Transactional
    fun reviewStore(review: Review, reviewPhotoList: List<ReviewPhoto>): UUID {
        reviewStore.store(review)
        reviewPhotoStore.store(reviewPhotoList)
        return review.reviewId
    }

    @Transactional
    fun reviewUpdate(reviewId: UUID, userId: UUID, content: String, attachedPhotoIds: List<UUID>): UUID {
        if (reviewReader.findUserIdByReviewId(reviewId) != userId) {
            throw InvalidJsonException(ErrorCode.INVALID_USER)
        }
        reviewStore.update(reviewId, content)
        reviewPhotoStore.update(reviewId, attachedPhotoIds)
        return reviewId
    }

    @Transactional
    fun reviewDelete(reviewId: UUID, userId: UUID): UUID {
        if (reviewReader.findUserIdByReviewId(reviewId) != userId) {
            throw InvalidJsonException(ErrorCode.INVALID_USER)
        }
        reviewStore.delete(reviewId)
        reviewPhotoStore.delete(reviewId)
        return reviewId
    }
}