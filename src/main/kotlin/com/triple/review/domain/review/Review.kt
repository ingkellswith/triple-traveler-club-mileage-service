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
    val reviewId: UUID,

    @Column
    val placeId: UUID,

    content: String,

    ) : BaseTimeEntity() {

    @Column
    var content: String = content
        protected set

    fun updateContent(changedContent: String): String {
        content = changedContent
        return content
    }
}


