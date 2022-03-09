package com.triple.review.controller

import com.triple.review.domain.point.PointService
import com.triple.review.dto.PointRetrieveResponseDto
import com.triple.review.dto.PointUpdateRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/events")
class PointApiController(
    private val pointService: PointService,
){
    @PostMapping
    fun UpdatePoint(@RequestBody pointUpdateRequest: PointUpdateRequestDto): ResponseEntity<UUID> {
        val response = pointService.updatePoint(pointUpdateRequest)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/{userId}")
    fun GetPointHistory(@PathVariable userId: UUID): ResponseEntity<PointRetrieveResponseDto> {
        val response = pointService.getPointHistory(userId)
        return ResponseEntity.ok().body(response)
    }
}