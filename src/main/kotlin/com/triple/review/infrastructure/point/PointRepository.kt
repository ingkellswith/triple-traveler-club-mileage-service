package com.triple.review.infrastructure.point

import com.triple.review.domain.point.Point
import com.triple.review.domain.point.PointHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PointRepository : JpaRepository<Point, Long> {
    fun findByUserId(userId: UUID): Point
}