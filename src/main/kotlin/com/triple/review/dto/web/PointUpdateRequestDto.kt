package com.triple.review.dto.web

import com.triple.review.domain.point.PointHistory
import com.triple.review.domain.review.Review
import com.triple.review.domain.review.ReviewPhoto
import java.util.*

data class PointUpdateRequestDto(
    val type: String,
    val action: String,
    val reviewId: UUID,
    val content: String,
    val attachedPhotoIds: List<UUID>,
    val userId: UUID,
    val placeId: UUID,
) {
    fun toPointHistoryEntity(changedPoint: Int): PointHistory {
        return PointHistory(
            userId = userId,
            placeId = placeId,
            serviceId = reviewId,
            point = changedPoint,
            type = type,
            action = action
        )
    }

    fun toReviewEntity(): Review {
        return Review(
            userId = userId,
            reviewId = reviewId,
            placeId = placeId,
            content = content,
        )
    }

    fun toReviewPhotoEntityList(): List<ReviewPhoto> {
        return attachedPhotoIds.map {
            ReviewPhoto(
                reviewId = reviewId,
                reviewPhotoId = it,
            )
        }
    }
}
