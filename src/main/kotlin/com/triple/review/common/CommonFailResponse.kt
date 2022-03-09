package com.triple.review.common

import com.triple.review.dto.web.StatusEnum

data class CommonFailResponse(
    var status: StatusEnum = StatusEnum.FAIL,
    val errorMessage: String? = null,
    val errorCode: ErrorCode? = null
)