package com.triple.review.infrastructure.review

import com.triple.review.domain.review.ReviewPhoto
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewPhotoRepository : JpaRepository<ReviewPhoto, Long> {
    fun countByReviewId(reviewId: UUID): Int
    fun deleteByReviewId(reviewId: UUID): Long
    fun deleteByReviewPhotoId(reviewPhotoId: UUID): Long
    fun findByReviewId(reviewId: UUID): List<ReviewPhoto>
}