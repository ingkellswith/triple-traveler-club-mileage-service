package com.triple.review

import com.triple.review.domain.point.PointService
import com.triple.review.dto.web.PointUpdateRequestDto
import com.triple.review.infrastructure.point.PointHistoryRepository
import com.triple.review.infrastructure.point.PointRepository
import com.triple.review.infrastructure.review.ReviewPhotoRepository
import com.triple.review.infrastructure.review.ReviewRepository
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@SpringBootTest
@RunWith(SpringRunner::class)
class PointServiceTest(
    val pointService: PointService,
    val pointHistoryRepository: PointHistoryRepository,
    val reviewRepository: ReviewRepository,
    val reviewPhotoRepository: ReviewPhotoRepository
) : BehaviorSpec() {

    override fun afterSpec(spec: Spec) {
        pointHistoryRepository.deleteAll()
        reviewRepository.deleteAll()
        reviewPhotoRepository.deleteAll()
        pointService.initPointForTest(UUID.fromString("3f06af63-a93c-11e4-9797-00505690773f"))
        pointService.initPointForTest(UUID.fromString("23194686-a5b8-47c8-9129-c24534aa2770"))
        pointService.initPointForTest(UUID.fromString("cadf5eff-497a-4715-ac3c-d03f90d82b2f"))
    }

    init {
        val userOneReviewId = UUID.randomUUID()
        val userTwoReviewId = UUID.randomUUID()
        val userThreeReviewId = UUID.randomUUID()

        val userOneUserId = UUID.fromString("3f06af63-a93c-11e4-9797-00505690773f")
        val userTwoUserId = UUID.fromString("23194686-a5b8-47c8-9129-c24534aa2770")
        val userThreeUserId = UUID.fromString("cadf5eff-497a-4715-ac3c-d03f90d82b2f")

        val placeOneId = UUID.fromString("f994fe2d-bf10-4505-ba9a-4cb4c4020944")
        val placeTwoId = UUID.fromString("73a07a3a-25ef-4f01-99e9-cc4ccbe8f469")

        given("ADD CASE 1: 유저1, 장소1인 경우") {
            val attachedPhotoIdOne = UUID.randomUUID()
            val attachedPhotoIdTwo = UUID.randomUUID()
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "ADD",
                reviewId = userOneReviewId,
                content = "빵이 맛있는 곳",
                attachedPhotoIds = listOf(attachedPhotoIdOne, attachedPhotoIdTwo),
                userId = userOneUserId,
                placeId = placeOneId,
            )
            `when`("유저1이 두 개의 사진이 포함된 리뷰를 장소1에 ADD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                and("특정 장소에 첫 리뷰 작성 시") {
                    then("변화된 포인트값인 3이 반환된다") {
                        response.changedPoint shouldBe 3
                    }
                }
            }
        }

        given("ADD CASE 2: 유저2, 장소1인 경우") {
            val attachedPhotoIdOne = UUID.randomUUID()
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "ADD",
                reviewId = userTwoReviewId,
                content = "가는 길이 힘들어도 꼭 가봐야 하는 곳",
                attachedPhotoIds = listOf(attachedPhotoIdOne),
                userId = userTwoUserId,
                placeId = placeOneId,
            )
            `when`("유저2가 한 개의 사진이 포함된 리뷰를 장소1에 ADD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                and("특정 장소에 작성한 첫 리뷰가 아닐 시") {
                    then("변화된 포인트값인 2가 반환된다") {
                        response.changedPoint shouldBe 2
                    }
                }
            }
        }

        given("ADD CASE 3: 유저3, 장소1인 경우") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "ADD",
                reviewId = userThreeReviewId,
                content = "너무 아름다운 곳",
                attachedPhotoIds = listOf(),
                userId = userThreeUserId,
                placeId = placeOneId,
            )
            `when`("유저3이 사진이 없는 리뷰를 장소1에 ADD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                and("특정 장소에 작성한 첫 리뷰가 아닐 시") {
                    then("변화된 포인트값인 1이 반환된다") {
                        response.changedPoint shouldBe 1
                    }
                }
            }
        }

        given("MOD CASE 1: 유저1, 장소1인 경우") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "MOD",
                reviewId = userOneReviewId,
                content = "편히 쉬다 갈 수 있는 곳",
                attachedPhotoIds = listOf(),
                userId = userOneUserId,
                placeId = placeOneId,
            )
            `when`("유저1이 사진을 모두 삭제한 상태로 MOD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("변화된 포인트값인 -1이 반환된다") {
                    response.changedPoint shouldBe -1
                }
            }
        }

        given("MOD CASE 2: 유저2, 장소1인 경우") {
            val attachedPhotoIdOne = UUID.randomUUID()
            val attachedPhotoIdTwo = UUID.randomUUID()
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "MOD",
                reviewId = userTwoReviewId,
                content = "커피가 맛있는 곳",
                attachedPhotoIds = listOf(attachedPhotoIdOne, attachedPhotoIdTwo),
                userId = userTwoUserId,
                placeId = placeOneId,
            )
            `when`("유저2가 사진을 1개에서 2개로 MOD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("원래 사진 리뷰를 했던 유저이므로, 변화된 포인트값인 0이 반환된다") {
                    response.changedPoint shouldBe 0
                }
            }
        }

        given("MOD CASE 3: 유저3, 장소1인 경우") {
            val attachedPhotoIdOne = UUID.randomUUID()
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "MOD",
                reviewId = userThreeReviewId,
                content = "추억을 쌓을 수 있는 곳",
                attachedPhotoIds = listOf(attachedPhotoIdOne),
                userId = userThreeUserId,
                placeId = placeOneId,
            )
            `when`("유저3이 사진을 0개에서 1개로 MOD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("원래 사진 리뷰를 안 했던 유저이므로, 변화된 포인트값인 1이 반환된다") {
                    response.changedPoint shouldBe 1
                }
            }
        }

        given("DELETE CASE 1: 유저1이 작성했던 리뷰 삭제") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "DELETE",
                reviewId = userOneReviewId,
                content = "",
                attachedPhotoIds = listOf(),
                userId = userOneUserId,
                placeId = placeOneId,
            )
            `when`("유저1이 장소1에 작성한 리뷰를 DELETE할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("글(1)+사진추가(1)+첫리뷰(1)-사진삭제(1)=2, 총 2포인트가 소멸되어야 한다.") {
                    response.changedPoint shouldBe -2
                }
            }
        }

        given("DELETE CASE 2: 유저2가 작성했던 리뷰 삭제") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "DELETE",
                reviewId = userTwoReviewId,
                content = "",
                attachedPhotoIds = listOf(),
                userId = userTwoUserId,
                placeId = placeOneId,
            )
            `when`("유저2가 장소1에 작성한 리뷰를 DELETE할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("글(1)+사진추가(1)=2, 총 2포인트가 소멸되어야 한다.") {
                    response.changedPoint shouldBe -2
                }
            }
        }

        given("DELETE CASE 3: 유저3이 작성했던 리뷰 삭제") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "DELETE",
                reviewId = userThreeReviewId,
                content = "",
                attachedPhotoIds = listOf(),
                userId = userThreeUserId,
                placeId = placeOneId,
            )
            `when`("유저3이 장소1에 작성한 리뷰를 DELETE할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("글(1)+사진추가(1)=2, 총 2포인트가 소멸되어야 한다.") {
                    response.changedPoint shouldBe -2
                }
            }
        }

        given("ADD CASE 4: 유저1, 장소2인 경우") {
            val attachedPhotoIdOne = UUID.randomUUID()
            val attachedPhotoIdTwo = UUID.randomUUID()
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "ADD",
                reviewId = userOneReviewId,
                content = "한 번쯤 방문할만 한 곳",
                attachedPhotoIds = listOf(attachedPhotoIdOne, attachedPhotoIdTwo),
                userId = userOneUserId,
                placeId = placeTwoId,
            )
            `when`("유저1이 두 개의 사진이 포함된 리뷰를 장소2에 ADD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                and("특정 장소에 첫 리뷰 작성 시") {
                    then("변화된 포인트값인 3이 반환된다") {
                        response.changedPoint shouldBe 3
                    }
                }
            }
        }

        given("ADD CASE 5: 유저2, 장소2인 경우") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "ADD",
                reviewId = userTwoReviewId,
                content = "놀러 가기 좋은 곳입니다!",
                attachedPhotoIds = listOf(),
                userId = userTwoUserId,
                placeId = placeTwoId,
            )
            `when`("유저2가 사진이 없는 리뷰를 장소2에 ADD할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("변화된 포인트값인 1이 반환된다") {
                    response.changedPoint shouldBe 1
                }
            }
        }

        given("DELETE CASE 4: 유저1, 장소2인 경우") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "DELETE",
                reviewId = userOneReviewId,
                content = "",
                attachedPhotoIds = listOf(),
                userId = userOneUserId,
                placeId = placeTwoId,
            )
            `when`("유저1이 장소2에 작성한 리뷰를 DELETE할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("글(1)+사진추가(1)+첫리뷰(1)=3, 총 3포인트가 소멸되어야 한다.") {
                    response.changedPoint shouldBe -3
                }
            }
        }

        given("DELETE CASE 5: 유저2, 장소2인 경우") {
            val pointUpdateRequestDto = PointUpdateRequestDto(
                type = "REVIEW",
                action = "DELETE",
                reviewId = userTwoReviewId,
                content = "",
                attachedPhotoIds = listOf(),
                userId = userTwoUserId,
                placeId = placeTwoId,
            )
            `when`("유저2가 장소2에 작성한 리뷰를 DELETE할 때") {
                val response = pointService.updatePoint(pointUpdateRequestDto)
                then("글(1)=1, 총 1포인트가 소멸되어야 한다.") {
                    response.changedPoint shouldBe -1
                }
            }
        }
    }
}