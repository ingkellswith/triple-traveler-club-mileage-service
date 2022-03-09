package com.triple.review.domain.point

import com.triple.review.domain.BaseTimeEntity
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "point_history")
class PointHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val userId: UUID,

    @Column
    val placeId: UUID,

    @Column
    val serviceId: UUID,

    @Column
    val point: Int,

    @Column
    val type: String,

    @Column
    val action: String,

    ) : BaseTimeEntity()