DROP SEQUENCE BOARDNOSEQ;
DROP SEQUENCE GROUPNOSEQ;
DROP TABLE ANSWERBOARD;

CREATE SEQUENCE BOARDNOSEQ;
CREATE SEQUENCE GROUPNOSEQ;

-- 글번호, 그룹번호, 그룹순서, 제목공백, 제목, 내용, 작성자, 작성일
CREATE TABLE ANSWERBOARD(
	BOARDNO NUMBER PRIMARY KEY,
	GROUPNO NUMBER NOT NULL,
	GROUPSEQ NUMBER NOT NULL,
	TITLETAB NUMBER NOT NULL,
	TITLE VARCHAR2(1000) NOT NULL,
	CONTENT VARCHAR2(4000) NOT NULL,
	WRITER VARCHAR2(100) NOT NULL,
	REGDATE DATE NOT NULL
);


-- 1번글 작성
INSERT INTO ANSWERBOARD
VALUES(BOARDNOSEQ.NEXTVAL, GROUPNOSEQ.NEXTVAL, 1, 0,
		'1번글입니다.', '1번 글을 썼는데... 답변형 어렵다.', '강사', SYSDATE);


--1번글에 답변 작성. UPDATE 하고 INSERT 해야 함

INSERT INTO ANSWERBOARD
VALUES(
		BOARDNOSEQ.NEXTVAL,
		(SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO=1),
		(SELECT GROUPSEQ FROM ANSWERBOARD WHERE BOARDNO=1)+1,
		(SELECT TITLETAB FROM ANSWERBOARD WHERE BOARDNO=1)+1,
		'RE:1번글에 답변',
		'답변',
		'무플방지위원회',
		SYSDATE
		);
		
--2번글 작성
INSERT INTO ANSWERBOARD VALUES
	(BOARDNOSEQ.NEXTVAL, GROUPNOSEQ.NEXTVAL, 1, 0,
	'2번글입니다.', '2번 글이 제대로 나올까?', '학생', SYSDATE);

--2번글 답변
--UPDATE
UPDATE ANSWERBOARD
SET GROUPSEQ = GROUPSEQ +1
WHERE GROUPNO = (SELECT GROUP FROM ANSWERBOARD WHERE BOARDNO = ?)
AND GROUPSEQ > (SELECT GROUPSEQ FROM ANSWERBOARD WHERE BOARDNO = ?);


	
	
	
--INSERT
--그룹번호: 답변을 달려고 하는 부모글의 그룹번호
--그룹순서: 답변을 달려고 하는 부모글의 그룹순서+1
--제목공백: 답변을 달려고 하는 부모글의 공백+1
INSERT INTO ANSWERBOARD VALUES(
			BOARDNOSEQ.NEXTVAL,
			(SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO=3),
			(SELECT GROUPSEQ+1 FROM ANSWERBOARD WHERE BOARDNO=3),
			(SELECT TITLETAB+1 FROM ANSWERBOARD WHERE BOARDNO=3),
			'RE:2번글에 답변', 
			'답변 달아 드립니다', 
			'반장', SYSDATE);

--2번글 답변글에 답변(보드 넘버 4에 답변)
--UPDATE
--INSERT
INSERT INTO ANSWERBOARD VALUES(
			BOARDNOSEQ.NEXTVAL,
			(SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO=4),
			(SELECT GROUPSEQ FROM ANSWERBOARD WHERE BOARDNO=4)+1,
			(SELECT TITLETAB+1 FROM ANSWERBOARD WHERE BOARDNO=4),
			'RE:RE:2번글입니다.',
			'진짜 어렵다.',
			'학생',
			SYSDATE);

--2번 원글에 답변글 다시 달기
--UPDATE (GROUPSEQ=눈에보여지는 순서를 바뀌어야 하기 때문)
--부모와 같은 그룹인 글들을 찾아서, 부모의 순서보다 큰 순서의 글들을 순서+1 해주자.
UPDATE ANSWERBOARD
SET GROUPSEQ = GROUPSEQ +1
WHERE GROUPNO = (SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO=3)
AND GROUPSEQ > (SELECT GROUPSEQ FROM ANSWERBOARD WHERE BOARDNO=3);

--INSERT
INSERT INTO ANSWERBOARD
VALUES(
		BOARDNOSEQ.NEXTVAL,
		(SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO =3),
		(SELECT GROUPSEQ+1 FROM ANSWERBOARD WHERE BOARDNO=3),
		(SELECT TITLETAB FROM ANSWERBOARD WHERE BOARDNO=3)+1,
		'RE:2번글의 2번째 리댓',
		'이제 좀 쉽네',
		'학생',
		SYSDATE
		);
		
--2번글의 2번째리댓(이제 좀 쉽네)에 RE 달기
--UPDATE (부모와 같은 그룹인 글들을 찾아서, 부모의 순서보다 큰 순서의 글들을 순서+1 해주자.)
UPDATE ANSWERBOARD
SET GROUPSEQ = GROUPSEQ +1
WHERE GROUPNO = (SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO=6)
AND GROUPSEQ > (SELECT GROUPSEQ FROM ANSWERBOARD WHERE BOARDNO=6);

INSERT INTO ANSWERBOARD
VALUES(BOARDNOSEQ.NEXTVAL,
		(SELECT GROUPNO FROM ANSWERBOARD WHERE BOARDNO=6),
		(SELECT GROUPSEQ+1 FROM ANSWERBOARD WHERE BOARDNO=6),
		(SELECT TITLETAB+1 FROM ANSWERBOARD WHERE BOARDNO=6),
		'RE:RE:이젠 좀 쉽죠?',
		'아 이제 쉽지 않다',
		'학생',
		SYSDATE
		);

		
--출력
SELECT BOARDNO, GROUPNO, GROUPSEQ, TITLETAB, TITLE, CONTENT, WRITER, REGDATE
FROM ANSWERBOARD
ORDER BY GROUPNO DESC, GROUPSEQ;