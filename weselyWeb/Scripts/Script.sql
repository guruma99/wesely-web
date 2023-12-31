-- 일반회원 테이블

CREATE SEQUENCE wmember_idx_seq;
CREATE TABLE wmember (
	idx NUMBER PRIMARY KEY,
	userid varchar2(50) NOT NULL,		-- 유저아이디
	password varchar2(200) NOT NULL,	-- 비밀번호
	username varchar2(50) NOT NULL,		-- 유저이름
	nickname varchar2(50),				-- 유저별명
	authority varchar2(50) NOT NULL,	-- 계정유형
	email varchar2(100),				-- 이메일
	phone varchar2(20)					-- 전화번호
);

SELECT * FROM wmember;

DELETE FROM wmember ;
SELECT * FROM memberImg ;

DROP TABLE WMEMBER ;
DROP TABLE memberImg ;

--===============================================================================
-- 회원 프로필 사진 테이블
CREATE SEQUENCE memberImg_id_seq;
CREATE TABLE memberImg(
	id NUMBER PRIMARY KEY,  -- 키필드
	ref NUMBER NOT NULL, -- 원본글 번호
	uuid varchar2(200) NOT NULL, -- 중복처리위한 키
	fileName varchar2(200) NOT NULL, -- 원본 파일명
	contentType varchar2(200) NOT NULL -- 파일 종류
);
--===============================================================================
-- 비즈니스회원 테이블
CREATE SEQUENCE bmember_id_seq;
CREATE TABLE bmember(
	idx NUMBER PRIMARY KEY,
	ref NUMBER NOT NULL ,
	bno varchar2(100) NOT NULL ,	-- 사업자번호
	bname varchar2(50) NOT NULL ,	-- 대표자성명
	store varchar2(100) NOT NULL	-- 대표자성명
);
DROP TABLE bmember;
SELECT * FROM bmember;
DELETE FROM bmember;
INSERT inTO bmember values(bmember_id_seq.nextval, '3808','1234567890','ddd','ddd');

--===============================================================================

-- 커뮤니티 테이블
CREATE SEQUENCE community_id_seq;
CREATE TABLE community(
	id NUMBER PRIMARY KEY, -- 키필드
	userid varchar2(50) NOT NULL, -- 아이디
	nickname varchar2(100) NOT NULL, -- 작성자
	contents varchar2(2000) NOT NULL, -- 글 내용
	regDate timestamp DEFAULT sysdate , -- 작성일
	readCount NUMBER DEFAULT 0 -- 조회수 증가 
	);
DROP TABLE COMMUNITY;
SELECT * FROM COMMUNITY;
DELETE FROM community WHERE userid = 'wesely';
-- 커뮤니티 댓글 테이블 
CREATE SEQUENCE comm_id_seq;
CREATE TABLE COMM(
	id NUMBER PRIMARY KEY,  -- 키필드
	REF NUMBER NOT NULL, -- 원본글 번호 
	name varchar2(100) NOT NULL, -- 작성자 이름
	content varchar2(2000) NOT NULL, -- 내용
	regdate timestamp DEFAULT sysdate -- 작성일
	);
SELECT * FROM comm;
DROP TABLE comm;
-- 이미지 파일 테이블
CREATE SEQUENCE communityImg_id_seq;
CREATE TABLE communityImg(
	id NUMBER PRIMARY KEY,  -- 키필드
	ref NUMBER NOT NULL, -- 원본글 번호
	uuid varchar2(200) NOT NULL, -- 중복처리위한 키
	fileName varchar2(200) NOT NULL, -- 원본 파일명
	contentType varchar2(200) NOT NULL -- 파일 종류
);

SELECT * FROM communityImg;
DROP TABLE communityImg;
SELECT community_id_seq.nextval,community_id_seq.currval  FROM dual;
insert into CommunityImg values (CommunityImg_id_seq.nextval, Community_id_seq.currval,'e2ff4d1b-b89d-423e-9f11-197573a4c515','1.png','image/png');

-- 좋아요 테이블
CREATE SEQUENCE good_id_seq; 
CREATE TABLE good(
	id NUMBER PRIMARY KEY, -- 키필드
	nickname varchar2(100) NOT NULL, -- 유저별명
	REF NUMBER NOT NULL, -- 원본글 번호
	whether NUMBER DEFAULT 0 -- 좋아요 여부
);
SELECT * FROM good;
DROP TABLE good;

-- 댓글 좋아요 테이블
CREATE SEQUENCE cgood_id_seq; 
CREATE TABLE cgood(
	id NUMBER PRIMARY KEY, -- 키필드
	nickname varchar2(100) NOT NULL, -- 유저별명
	REF NUMBER NOT NULL, -- 원본글 번호
	whether NUMBER DEFAULT 0 -- 좋아요 여부
);
SELECT * FROM good;
DROP TABLE good;
--===============================================================================

-- ✅ 리뷰가 작성가능한 시설섹션을 만들자
-- 1. 시설섹션에 사용할 시퀀스
CREATE SEQUENCE store_id_seq;
DROP SEQUENCE store_id_seq;
-- 2. 게시판 테이블 생성
CREATE TABLE store(
	id NUMBER PRIMARY KEY,  -- 키필드
	userid varchar2(50), -- 권한자 아이디 : 비즈니스계정
	name varchar2(50) NOT NULL, -- 매장명
	address varchar2(100) NOT NULL, -- 매장주소
	opening varchar2(300), -- 매장운영시간
	tel varchar2(30), -- 매장번호
	description varchar2(2000), -- 매장설명
	hashTag varchar2(70), -- 매장해쉬태그
	kakaoId varchar2(50), -- kakaoId
	region varchar2(100) -- 위치 예) 중구문화동
);

insert into store values (store_id_seq.nextval, 'test', '탄방동아임일리터','ㅁㄴㅇㄻㄴㅇㄹ','운영시간9:00', '000000', '설명이다', '#hasttag#coffe', '1122826292','서구탄방동');

-- 3. 이미지 테이블에 사용할 시퀀스
CREATE SEQUENCE storeImg_id_seq;
-- 4. 이미지 파일 테이블
CREATE TABLE storeImg(
	id NUMBER PRIMARY KEY,  -- 키필드
	ref NUMBER NOT NULL, -- 원본글 번호
	uuid varchar2(200) NOT NULL, -- 중복처리위한 키
	fileName varchar2(200) NOT NULL, -- 원본 파일명
	contentType varchar2(200) NOT NULL, -- 파일 종류
	iorder NUMBER  -- 이미지 순서
);

SELECT store_id_seq.nextval,store_id_seq.currval  FROM dual;


-- 5. 리뷰 테이블에 사용할 시퀀스
CREATE SEQUENCE sreview_id_seq;

-- 6. 리뷰 테이블 생성
CREATE TABLE storeReview(
	id NUMBER PRIMARY KEY,  -- 키필드
	REF NUMBER NOT NULL, -- 원본글 번호
	nickname varchar2(50) NOT NULL, -- 유저닉네임
	userProfile varchar2(100) NOT NULL, -- 유저프로필사진
	star NUMBER NOT NULL, -- 별점
	content varchar2(2000) NOT NULL, -- 내용
	regdate timestamp DEFAULT sysdate -- 작성일
);

DROP TABLE store;
DROP TABLE storeImg;
DROP TABLE storeReview;

SELECT * FROM store;
SELECT * FROM storeReview;
SELECT * FROM storeImg;

SELECT * FROM store WHERE kakaoId IN (1122826292,1122826293);


