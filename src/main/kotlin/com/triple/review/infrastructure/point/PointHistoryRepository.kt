package com.triple.review.infrastructure.point

import com.triple.review.domain.point.PointHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PointHistoryRepository : JpaRepository<PointHistory, Long> {
    fun findByUserId(userId: UUID): List<PointHistory>
    @Query("select SUM(p.point) from PointHistory p where serviceId = :reviewId")
    fun findPointSumByReviewId(@Param("reviewId") reviewId: UUID): Int?
}