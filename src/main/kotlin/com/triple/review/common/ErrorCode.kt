package com.triple.review.common


enum class ErrorCode(val errorMsg: String) {
    INVALID_JSON("json의 값이 올바르지 않습니다."),
    INVALID_USER("json으로 요청된 userId에 해당하는 유저의 리뷰가 아닌 경우 삭제하거나 변경할 수 없습니다."),
    NO_RECORD("데이터베이스에 해당 레코드의 값이 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR("처리할 수 없습니다."),
}