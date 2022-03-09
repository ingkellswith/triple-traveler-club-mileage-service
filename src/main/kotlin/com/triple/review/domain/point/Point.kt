package com.triple.review.domain.point

import com.triple.review.domain.BaseTimeEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "point")
class Point(userId: UUID, pointSum: Int) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column
    val userId: UUID = userId

    @Column
    var pointSum: Int = pointSum
        protected set

    fun updatePointSum(changedPoint: Int): Int {
        pointSum+=changedPoint
        return pointSum
    }
}