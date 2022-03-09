package com.triple.review.infrastructure.review

import com.triple.review.common.ErrorCode
import com.triple.review.common.exception.InvalidJsonException
import com.triple.review.domain.review.Review
import org.springframework.stereotype.Component
import java.util.*

@Component
class ReviewStore(
    private val reviewRepository: ReviewRepository
){
    fun store(review: Review): Review {
        return reviewRepository.save(review)
    }

    fun update(reviewId: UUID, content: String): UUID? {
        val review=reviewRepository.findByReviewId(reviewId) ?: throw InvalidJsonException(ErrorCode.INVALID_JSON)
        review.updateContent(content)
        return review.reviewId
    }

    fun delete(reviewId: UUID): Long{
        return reviewRepository.deleteByReviewId(reviewId)
    }
}