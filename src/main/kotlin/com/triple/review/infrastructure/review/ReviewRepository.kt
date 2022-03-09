package com.triple.review.infrastructure.review

import com.triple.review.domain.review.Review
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewRepository : JpaRepository<Review, Long> {
    fun countByPlaceId(placeId: UUID): Int
    fun findByReviewId(reviewId: UUID): Review?
    fun deleteByReviewId(reviewId: UUID): Long
}