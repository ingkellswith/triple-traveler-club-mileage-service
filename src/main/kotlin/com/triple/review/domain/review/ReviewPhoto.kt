package com.triple.review.domain.review

import com.triple.review.domain.BaseTimeEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "review_photo")
class ReviewPhoto(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val reviewPhotoId: UUID,

    @Column
    val reviewId: UUID,

    ) : BaseTimeEntity()
