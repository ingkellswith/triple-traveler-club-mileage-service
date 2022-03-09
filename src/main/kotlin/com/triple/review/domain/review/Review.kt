package com.triple.review.domain.review

import com.triple.review.domain.BaseTimeEntity
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "review")
class Review(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val userId: UUID,

    @Column
    var reviewId: UUID,

    @Column
    var placeId: UUID,

    @Column
    var content: String,

    ) : BaseTimeEntity()
