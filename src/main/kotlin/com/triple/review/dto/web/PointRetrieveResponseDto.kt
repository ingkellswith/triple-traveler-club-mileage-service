package com.triple.review.dto.web

import com.triple.review.domain.point.PointHistory
import java.util.*

data class PointRetrieveResponseDto(
    var status: StatusEnum = StatusEnum.SUCCESS,
    val userId: UUID,
    val pointSum: Int,
    val pointHistoryList: List<PointHistoryInfo>,
) {
    data class PointHistoryInfo(
        val type: String,
        val action: String,
        val serviceId: UUID,
        val placeId: UUID,
        val point: Int
    )

    companion object {
        fun of(pointHistory: List<PointHistory>, pointSum: Int, userId: UUID): PointRetrieveResponseDto {
            val pointHistoryInfo = pointHistory.map {
                PointHistoryInfo(
                    type = it.type,
                    action = it.action,
                    serviceId = it.serviceId,
                    placeId = it.placeId,
                    point = it.point
                )
            }
            return PointRetrieveResponseDto(
                pointSum = pointSum,
                userId = userId,
                pointHistoryList = pointHistoryInfo
            )
        }
    }
}