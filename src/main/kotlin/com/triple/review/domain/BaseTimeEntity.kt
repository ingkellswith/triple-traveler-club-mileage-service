package com.triple.review.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreatedDate
    lateinit var createdAt : LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    @PrePersist
    fun onPrePersist(){
        this.createdAt = createdAt.withNano(0)
        this.updatedAt = this.createdAt
    }

    @PreUpdate
    fun onPreUpdate(){
        this.updatedAt = updatedAt.withNano(0)
    }

}