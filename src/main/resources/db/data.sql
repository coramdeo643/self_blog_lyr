-- User 테이블 데이터 (5명의 사용자)
INSERT INTO user_tb (username, password, email, profile_image_path, created_at) VALUES
('admin', '1234', 'admin@blog.com', '/img/img1.jpg', NOW()),
('ssar', '1234', 'ssar@nate.com', '/img/img2.jpg', NOW()),
('cos', '1234', 'cos@gmail.com', '/img/img3.jpg', NOW()),
('hong', '1234', 'hong@naver.com', '/img/img4.jpg', NOW()),
('kim', '1234', 'kim@daum.net', '/img/img5.jpg', NOW());

-- 2단계: Board 테이블 데이터 (10개의 게시글)
-- 주의: user_id는 위에서 생성된 사용자의 id를 참조

-- admin 사용자가 작성한 게시글 (3개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('블로그 개설을 환영합니다!', '안녕하세요! 새로운 블로그가 오픈했습니다. 많은 관심과 참여 부탁드립니다.', 1, NOW()),
('공지사항: 이용수칙 안내', '블로그 이용 시 지켜야 할 기본적인 수칙들을 안내드립니다. 건전한 소통 문화를 만들어가요.', 1, NOW()),
('업데이트 소식', '새로운 기능들이 추가되었습니다. 댓글 기능과 좋아요 기능을 곧 만나보실 수 있습니다.', 1, NOW());

-- ssar 사용자가 작성한 게시글 (3개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('Spring Boot 학습 후기', 'Spring Boot를 처음 배우면서 느낀 점들을 공유합니다. JPA가 정말 편리하네요!', 2, NOW()),
('JPA 연관관계 정리노트', '오늘 배운 @ManyToOne, @OneToMany 연관관계에 대해 정리해봤습니다. 헷갈리는 부분이 많아요.', 2, NOW()),
('코딩테스트 문제 추천', '백준과 프로그래머스에서 풀어볼 만한 문제들을 추천드립니다. 알고리즘 공부 화이팅!', 2, NOW());

-- cos 사용자가 작성한 게시글 (2개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('React vs Vue 비교', '프론트엔드 프레임워크 선택에 고민이 많았는데, 각각의 장단점을 비교해봤습니다.', 3, NOW()),
('개발자 취업 팁 공유', '신입 개발자로 취업하면서 도움이 되었던 팁들을 공유합니다. 포트폴리오가 중요해요!', 3, NOW());

-- hong 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('첫 번째 게시글입니다', '안녕하세요! 블로그에 처음 글을 올려봅니다. 앞으로 자주 소통해요~', 4, NOW());

-- kim 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('맛집 추천 - 강남역 근처', '강남역 근처에서 점심 먹기 좋은 맛집들을 추천드립니다. 가성비도 좋아요!', 5, NOW());

-- 사진 게시판 (article_tb) 샘플 데이터
INSERT INTO article_tb (title, content, user_id, picture_image_path, created_at) VALUES
('제주도 해변에서', '지난 주말 다녀온 제주도 협재 해수욕장입니다. 날씨가 정말 좋았어요!', 2, '/img/photo1.jpg', NOW()),
('우리집 냥이', '우리집 고양이 ''나비''에요. 너무 귀엽죠?', 3, '/img/photo2.jpg', NOW()),
('부산 야경', '부산 더베이101에서 찍은 야경 사진입니다. 홍콩 부럽지 않네요!', 4, '/img/photo3.jpg', NOW()),
('오늘의 저녁', '직접 만든 파스타! 생각보다 맛있게 돼서 뿌듯하네요.', 5, '/img/photo4.jpg', NOW()),
('가을 단풍놀이', '설악산 단풍 구경 다녀왔습니다. 울긋불긋 정말 아름다웠어요.', 1, '/img/photo5.jpg', NOW()),
('강아지와 산책', '저희 집 댕댕이랑 공원 산책 중! 신나서 꼬리가 떨어질 것 같아요.', 2, '/img/photo6.jpg', NOW()),
('도쿄 타워에서', '일본 여행 중 찍은 도쿄 타워입니다. 야경이 정말 멋졌어요.', 3, '/img/photo7.jpg', NOW()),
('캠핑의 밤', '친구들과 함께한 캠핑! 모닥불 피워놓고 이야기 나누는 시간이 좋았어요.', 4, '/img/photo8.jpg', NOW()),
('새로운 가족 앵무새', '새로운 가족이 된 앵무새 ''초록이''를 소개합니다. 말을 배우는 중이에요!', 5, '/img/photo9.jpg', NOW()),
('파리의 에펠탑', '꿈에 그리던 파리 여행! 에펠탑 앞에서 찍은 인생샷입니다.', 1, '/img/photo10.jpg', NOW());

INSERT INTO comment_tb (comment, article_id, user_id, created_at) VALUES
('와, 협재 해수욕장 날씨 정말 최고였네요! 사진만 봐도 힐링돼요.', 1, 2, NOW()),
('제주도 해변에서 시원한 바람 맞고 싶네요! 부러워요!', 1, 3, NOW()),
('나비 너무 귀여워요! 어떤 장난감을 좋아하나요?', 2, 1, NOW()),
('냥이들만의 매력이 있죠! 사진 더 올려주세요.', 2, 3, NOW()),
('더베이101 야경 정말 끝내주죠! 부산의 밤은 언제나 아름다워요.', 3, 2, NOW()),
('부산 야경하면 더베이101이죠! 홍콩 부럽지 않다는 말에 완전 동의해요!', 3, 4, NOW()),
('직접 만드신 파스타라니! 비주얼도 완벽하고 맛도 최고였겠어요!', 4, 3, NOW()),
('와, 파스타 정말 맛있어 보여요! 레시피 공유해주실 수 있나요?', 4, 5, NOW()),
('설악산 단풍 정말 아름답죠! 가을에는 역시 산이 최고예요.', 5, 4, NOW()),
('단풍놀이 다녀오셨군요! 사진으로만 봐도 울긋불긋 너무 예뻐요.', 5, 1, NOW()),
('댕댕이랑 산책이라니! 강아지가 정말 신나 보이고 귀여워요.', 6, 5, NOW()),
('꼬리가 떨어질 것 같다는 표현이 너무 귀여워요! 즐거운 산책 되셨겠네요.', 6, 2, NOW()),
('도쿄 타워 야경 정말 멋지죠! 저도 일본 여행 갔을 때 감동했어요.', 7, 1, NOW()),
('도쿄 타워에서 찍은 사진인가요? 야경이 정말 환상적이네요!', 7, 3, NOW()),
('캠핑의 밤은 역시 모닥불이죠! 친구들과의 좋은 추억 만드셨겠어요.', 8, 2, NOW()),
('모닥불 피워놓고 이야기 나누는 시간이라니, 생각만 해도 힐링되네요.', 8, 4, NOW()),
('앵무새 초록이 너무 예뻐요! 말을 잘 배우면 정말 신기하겠네요.', 9, 3, NOW()),
('새로운 가족이 된 걸 축하해요! 초록이랑 행복한 시간 보내세요.', 9, 5, NOW()),
('에펠탑 앞에서 인생샷이라니! 정말 꿈같은 파리 여행이었겠어요.', 10, 4, NOW()),
('파리 에펠탑! 저도 언젠가 꼭 가보고 싶어요. 사진 정말 멋져요!', 10, 1, NOW());

-- 댓글 테이블 데이터 (각 게시글에 댓글들을 추가)

-- 1번 게시글 (admin의 '블로그 개설을 환영합니다!')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('축하드립니다! 새로운 블로그 기대되네요.', 1, 2, NOW()),
('관리자님 수고 많으셨습니다. 좋은 컨텐츠 부탁드려요!', 1, 3, NOW()),
('드디어 오픈했군요. 자주 방문하겠습니다.', 1, 4, NOW());

-- 2번 게시글 (admin의 '공지사항: 이용수칙 안내')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('이용수칙 잘 읽어보겠습니다.', 2, 2, NOW()),
('건전한 소통 문화 만들기에 동참하겠습니다!', 2, 5, NOW());

-- 3번 게시글 (admin의 '업데이트 소식')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('댓글 기능 추가 감사합니다!', 3, 2, NOW()),
('좋아요 기능도 빨리 나왔으면 좋겠어요.', 3, 3, NOW()),
('업데이트 소식 감사합니다. 잘 사용하겠습니다.', 3, 4, NOW()),
('새로운 기능들이 기대됩니다.', 3, 5, NOW());

-- 4번 게시글 (ssar의 'Spring Boot 학습 후기')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('저도 Spring Boot 공부 중인데 많은 도움이 되었습니다!', 4, 1, NOW()),
('JPA 정말 편리하죠. 처음엔 어려웠지만 익숙해지면 좋더라구요.', 4, 3, NOW()),
('학습 후기 공유해주셔서 감사합니다.', 4, 5, NOW());

-- 5번 게시글 (ssar의 'JPA 연관관계 정리노트')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('연관관계 정말 헷갈리죠 ㅠㅠ 정리 잘 해주셨네요!', 5, 1, NOW()),
('@ManyToOne 부분이 특히 어려웠는데 덕분에 이해했습니다.', 5, 3, NOW()),
('저도 공부하면서 참고하겠습니다.', 5, 4, NOW()),
('양방향 매핑 부분도 추가로 설명해주시면 좋을 것 같아요.', 5, 5, NOW());

-- 6번 게시글 (ssar의 '코딩테스트 문제 추천')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('문제 추천 감사합니다! 바로 풀어보겠습니다.', 6, 1, NOW()),
('백준 문제 중에서 어떤 걸 먼저 풀어보면 좋을까요?', 6, 4, NOW());

-- 7번 게시글 (cos의 'React vs Vue 비교')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('React 쪽이 더 인기가 많은 것 같긴 하네요.', 7, 1, NOW()),
('Vue가 더 배우기 쉽다고 들었는데 실제로는 어떤가요?', 7, 2, NOW()),
('둘 다 써봤는데 각각 장단점이 있는 것 같아요.', 7, 4, NOW()),
('프로젝트 성격에 따라 선택하면 될 것 같습니다.', 7, 5, NOW());

-- 8번 게시글 (cos의 '개발자 취업 팁 공유')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('취업 준비 중인데 정말 유용한 정보네요!', 8, 2, NOW()),
('포트폴리오 작성 방법도 자세히 알려주세요.', 8, 4, NOW()),
('면접 준비는 어떻게 하셨나요?', 8, 5, NOW());

-- 9번 게시글 (hong의 '첫 번째 게시글입니다')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('첫 게시글 축하드려요! 환영합니다.', 9, 1, NOW()),
('앞으로 자주 소통해요~', 9, 2, NOW()),
('좋은 게시글 기대하겠습니다!', 9, 3, NOW());

-- 10번 게시글 (kim의 '맛집 추천 - 강남역 근처')에 대한 댓글
INSERT INTO reply_tb (comment, board_id, user_id, created_at) VALUES
('강남역 자주 가는데 맛집 정보 감사해요!', 10, 1, NOW()),
('가성비 좋은 곳 추천해주셔서 고마워요.', 10, 2, NOW()),
('저도 가봐야겠네요. 위치 정보도 알려주세요.', 10, 3, NOW()),
('점심 메뉴 추천도 해주시면 좋을 것 같아요.', 10, 4, NOW());