package com.triple.review.domain.point

import com.triple.review.domain.BaseTimeEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "point_history")
class PointHistory(
    userId: UUID,
    placeId: UUID,
    serviceId: UUID,
    point: Int,
    type: String,
    action: String
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column
    val userId: UUID = userId

    @Column
    val placeId: UUID = placeId

    @Column
    val serviceId: UUID = serviceId

    @Column
    var point: Int = point
        protected set

    @Column
    val type: String = type

    @Column
    val action: String = action

}