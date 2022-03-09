# 트리플 여행자 클럽 마일리지 서비스

![triple](https://user-images.githubusercontent.com/55550753/157543399-c5c9e345-eace-4655-aee2-df9769490b8b.png)

# 실행 IDE

- Intellij Idea Community

# Used Stacks

- Kotlin
- gradle
- Spring MVC
- Spring Data Jpa
- Kotest
- MySql
- docker-compose

# 프로젝트 구성

src/main/kotlin/com/triple/www/

- common : 공통적으로 사용되는 exception 정의 및 exception 처리를 위한 controllerAdvice 사용
- config : 설정 정보
- controller : web 계층의 컨트롤러
- domain : 엔티티 및 서비스
- dto : web 계층의 데이터 전달 담당 및 내부 데이터 전달 담당
- infrastructure : 데이터베이스 접근 계층으로 '읽기 작업만 하는가'를 기준으로 Reader와 Store로 분류

src/test/kotlin/com/triple/www/

- PointServiceTest : Kotest를 사용해서 PointService에 대한 스프링 통합 테스트 코드를 작성

# 프로젝트 실행 방법

1. git clone https://github.com/ingkellswith/triple-traveler-club-mileage-service.git
2. 클론 후 gradle을 이용한 프로젝트 dependency 패치
3. cd ./docker-compose
4. MySQL실행을 위한 도커 컨테이너를 실행합니다, docker-compose up -d
5. 실행 후 로컬 호스트의 13306포트에서 MySQL의 도커 컨테이너에 접속할 수 있습니다.
6. db접속 정보는 '유저: user, 비밀번호: user-password' 입니다.
7. db접속 후 DDL, DML 실행을 실행해야 합니다 - DDL, DML을 위한 SQL은 db/init_ddl.sql, db/init_dml.sql에 위치합니다.
8. 최상위 폴더로 이동, cd ..
9. ./gradlew bootRun 혹은 Intellij의 run application을 사용해서 이 프로젝트 실행

# API

```text
# 포인트 적립 api
POST localhost:8080/events

# 포인트 조회 api
GET localhost:8080/events/point/{userId}
```

# TEST CASE

1. src/test/kotlin/com/triple/www/PointServiceTest.kt 에 테스트 케이스에 대해 테스트 코드를 작성했습니다.
2. 로컬 api테스트는 ./triple.postman.json에 테스트 케이스에 대한 json이 있으니 포스트맨에서 import해서 테스트해볼 수 있습니다.

# MySQL 테이블과 JPA의 엔티티

1. 이 프로젝트에 사용된 테이블은 총 6개이나 그 중 user테이블과 place테이블은 테스트 데이터를 사용했기 때문에 테이블만 만들고, 따로 엔티티를 만들지 않았습니다.
2. 포인트 관련 테이블은 point와 point_history테이블로 나누어 point테이블은 포인트 합계 및 일종의 캐시 역할을 하는 테이블로, point_history테이블은
이력만 따로 관리할 수 있도록 했습니다.
3. 리뷰 관련 테이블은 review와 review_photo테이블로 나누어 구성했습니다.