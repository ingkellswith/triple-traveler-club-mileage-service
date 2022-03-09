package com.triple.review.controller

import com.triple.review.domain.point.PointService
import com.triple.review.dto.web.PointManipulationResponseDto
import com.triple.review.dto.web.PointRetrieveResponseDto
import com.triple.review.dto.web.PointUpdateRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/events")
class PointApiController(
    private val pointService: PointService,
){
    @PostMapping
    fun UpdatePoint(@RequestBody pointUpdateRequest: PointUpdateRequestDto): ResponseEntity<PointManipulationResponseDto> {
        val response = pointService.updatePoint(pointUpdateRequest)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/point/{userId}")
    fun GetPointHistory(@PathVariable userId: UUID): ResponseEntity<PointRetrieveResponseDto> {
        val response = pointService.getPointHistory(userId)
        return ResponseEntity.ok().body(response)
    }
}