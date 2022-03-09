package com.triple.review.infrastructure.review

import com.triple.review.domain.review.ReviewPhoto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ReviewPhotoStore(
    private val reviewPhotoRepository: ReviewPhotoRepository
) {
    fun store(reviewPhotoList: List<ReviewPhoto>): List<ReviewPhoto> {
        return reviewPhotoList.map { reviewPhotoRepository.save(it) }
    }

    fun delete(reviewId: UUID): Long {
        return reviewPhotoRepository.deleteByReviewId(reviewId)
    }

    fun update(reviewId: UUID, attachedPhotoIds: List<UUID>): UUID {
        val reviewPhotos: List<ReviewPhoto> = reviewPhotoRepository.findByReviewId(reviewId)
        val reviewPhotoSet = reviewPhotos.map { it.reviewPhotoId }.toSet()
        val attachedPhotoIdSet = attachedPhotoIds.toSet()

        val photoIdToDelete = reviewPhotoSet.subtract(attachedPhotoIdSet)
        val photoIdToSave = attachedPhotoIdSet.subtract(reviewPhotoSet)

        photoIdToDelete.forEach {
            reviewPhotoRepository.deleteByReviewPhotoId(it)
        }

        photoIdToSave.forEach {
            reviewPhotoRepository.save(
                ReviewPhoto(
                    reviewId = reviewId,
                    reviewPhotoId = it
                )
            )
        }

        return reviewId
    }
}