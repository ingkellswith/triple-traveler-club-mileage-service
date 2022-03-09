create table user
(
    id            bigint auto_increment primary key comment 'id',
    user_id 	  BINARY(16) not null comment '유저 id',
    created_at    datetime(6) not null comment '생성 일시',
    updated_at    datetime(6) not null comment '수정 일시'
) comment 'user 테이블은 여행자의 계정 정보를 저장하는 테이블입니다.' charset = utf8mb4;

create index users_idx01 on user (user_id);

create table place
(
    id            bigint auto_increment primary key comment 'id',
    place_id 	  BINARY(16) not null comment '장소 id',
    created_at    datetime(6) not null comment '생성 일시',
    updated_at    datetime(6) not null comment '수정 일시'
) comment 'place 테이블은 여행 장소를 저장하는 테이블입니다.' charset = utf8mb4;

create index place_idx01 on place (place_id);

create table review
(
    id            bigint auto_increment primary key comment 'id',
	user_id		  BINARY(16) not null comment '유저 id',
	place_id 	  BINARY(16) not null comment '장소 id',
	review_id	  BINARY(16) not null comment '리뷰 id',
	content		  text not null comment '내용',
    created_at    datetime(6) not null comment '생성 일시',
    updated_at    datetime(6) not null comment '수정 일시',
    CONSTRAINT review_un UNIQUE KEY (user_id,place_id)
) comment 'review 테이블은 여행자의 리뷰 정보를 저장하는 테이블입니다.' charset = utf8mb4;

create index review_idx01 on review (place_id);
create index review_idx02 on review (review_id);

create table review_photo
(
    id            	  bigint auto_increment primary key comment 'id',
	review_id	  	  BINARY(16) not null comment '리뷰 id',
	review_photo_id	  BINARY(16) not null comment '리뷰에 사용된 사진 id',
    created_at    	  datetime(6) not null comment '생성 일시',
    updated_at    	  datetime(6) not null comment '수정 일시'
) comment 'review_photo 테이블은 여행자가 리뷰를 작성할 때 사용한 사진을 저장하는 테이블입니다.' charset = utf8mb4;

create index review_photo_idx01 on review_photo (review_id);
create index review_photo_idx02 on review_photo (review_photo_id);


create table point
(
    id            	  bigint auto_increment primary key comment 'id',
	user_id	  	  	  BINARY(16) not null comment '유저 id',
	point_sum	  	  int not null comment '현재 포인트 총합',
    created_at    	  datetime(6) not null comment '생성 일시',
    updated_at    	  datetime(6) not null comment '수정 일시'
) comment 'point 테이블은 여행자의 현 포인트 총합을 저장하는 테이블입니다.' charset = utf8mb4;

create index point_idx01 on point (user_id);

create table point_history
(
    id            	  bigint auto_increment primary key comment 'id',
	user_id	  	  	  BINARY(16) not null comment '유저 id',
	place_id	  	  BINARY(16) not null comment '장소 id',
	service_id	  	  BINARY(16) not null comment '서비스 id(ex. 리뷰 id)',
	point	  	  	  int not null comment '포인트',
	type	  	  	  VARCHAR(30) not null comment '히스토리가 쌓이게 된 사유/타입',
	action	  	  	  VARCHAR(30) not null comment '액션(ADD, MOD, DELETE)',
    created_at    	  datetime(6) not null comment '생성 일시',
    updated_at    	  datetime(6) not null comment '수정 일시'
) comment 'point_history 테이블은 여행자의 포인트 변경 이력을 저장하는 테이블입니다.' charset = utf8mb4;

create index point_history_idx01 on point_history (user_id);
create index point_history_idx02 on point_history (service_id);