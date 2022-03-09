package com.triple.review.infrastructure.review

import org.springframework.stereotype.Component
import java.util.*

@Component
class ReviewReader(
    private val reviewRepository: ReviewRepository
) {
    fun findIfCurrentReviewIsFirstReview(placeId: UUID): Boolean{
        val placeReviewCount = reviewRepository.countByPlaceId(placeId)
        return placeReviewCount == 0
    }

    fun findUserIdByReviewId(reviewId: UUID): UUID? {
        val review= reviewRepository.findByReviewId(reviewId)
        return review?.userId ?: null
    }
}