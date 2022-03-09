package com.triple.review.infrastructure.point

import com.triple.review.domain.point.Point
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PointRepository : JpaRepository<Point, Long> {
    fun findByUserId(userId: UUID): Point?
}