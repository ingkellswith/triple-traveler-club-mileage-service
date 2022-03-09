package com.triple.review.infrastructure.review

import org.springframework.stereotype.Component
import java.util.*

@Component
class ReviewPhotoReader(
    private val reviewPhotoRepository: ReviewPhotoRepository,
) {
    fun findIfBeforeReviewHasPhoto(reviewId: UUID): Boolean {
        val beforeReviewCount = reviewPhotoRepository.countByReviewId(reviewId)
        return beforeReviewCount != 0
    }
}
