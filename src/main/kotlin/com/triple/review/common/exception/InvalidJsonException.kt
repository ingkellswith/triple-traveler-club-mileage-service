package com.triple.review.common.exception

import com.triple.review.common.ErrorCode

class InvalidJsonException : RuntimeException {
    private var errorCode: ErrorCode

    constructor(errorCode: ErrorCode) : super(errorCode.errorMsg) {
        this.errorCode = errorCode
    }

}