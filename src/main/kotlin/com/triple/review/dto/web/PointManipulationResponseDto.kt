package com.triple.review.dto.web

import java.util.*

data class PointManipulationResponseDto(
    var status: StatusEnum = StatusEnum.SUCCESS,
    val userId: UUID,
    val reviewId: UUID
)