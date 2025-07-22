# 🚀 Self-Blog Project_YR

Java와 Spring Boot를 기반으로 제작된 간단한 블로그 애플리케이션입니다. 이 프로젝트는 사용자 관리, 게시글 및 댓글 기능, 프로필 이미지 업로드 등 웹 애플리케이션의 핵심적인 기능들을 포함하고 있습니다.


## ✨ 주요 기능

### 👤 사용자 (User)
- **회원가입 및 로그인/로그아웃**: 세션(Session)을 이용한 사용자 인증 관리
- **회원정보 수정**: 비밀번호 등 개인 정보 수정 기능
- **프로필 이미지 관리**: 프로필 이미지 업로드, 변경 및 삭제 기능

### 📝 게시판 (Board)
- **게시글 CRUD**: 게시글 작성, 조회, 수정, 삭제 기능
- **페이지네이션**: 게시글 목록을 페이지 단위로 조회
- **상세 조회**: 개별 게시글 상세 내용 및 댓글 조회
- **권한 관리**: 본인이 작성한 게시글만 수정 및 삭제 가능

### 💬 댓글 (Reply)
- **댓글 작성 및 삭제**: 각 게시글에 대한 댓글 작성 및 삭제 기능
- **권한 관리**: 본인이 작성한 댓글만 삭제 가능

### 📸 사진 갤러리 (Article & Comment)
- 게시판과 별도로 분리된 사진 중심의 메모(Article) 기능
- **사진 CRUD**: 사진과 함께 메모 작성, 조회, 수정, 삭제
- **댓글 기능**: 각 메모에 대한 댓글(Comment) 작성 및 삭제 

### ⚙️ 공통 기능
- **인증 처리**: Spring Interceptor를 활용하여 로그인되지 않은 사용자의 접근 제어
- **예외 처리**: `@ControllerAdvice`를 사용하여 HTTP 상태 코드(400, 401, 403, 404, 500)에 따른 맞춤형 에러 페이지 처리
- **파일 업로드**: 사용자가 업로드한 프로필 이미지를 서버에 저장 및 관리

---

## 🛠️ 기술 스택

- **Backend**: `Java 21`, `Spring Boot 3.x`, `Spring MVC`, `Spring Data JPA`, `Hibernate`
- **Frontend**: `Mustache`, `Bootstrap 5`, `JavaScript`, `jQuery`
- **Database**: `H2 (In-memory)`
- **Etc**: `Lombok`, `SLF4J`, `Apache Commons Lang3`

---

## 📂 프로젝트 구조
```
self_blog_lyr/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/tenco/blog/
│   │   │       ├── _core/                  # ⚙️ 공통 핵심 모듈
│   │   │       │   ├── common/             #    - 공통 DTO (PageLink)
│   │   │       │   ├── config/             #    - 설정 (WebMvcConfig)
│   │   │       │   ├── errors/             #    - 예외 처리 (ExceptionHandler, Custom Exceptions)
│   │   │       │   └── interceptor/        #    - 인터셉터 (LoginInterceptor)
│   │   │       ├── user/                   # 👤 사용자 (회원가입, 로그인, 정보수정, 프로필)
│   │   │       ├── board/                  # 📝 게시판
│   │   │       ├── reply/                  # 💬 게시판 댓글
│   │   │       ├── article/                # 📸 사진 갤러리
│   │   │       ├── comment/                # 💬 사진 갤러리 댓글
│   │   │       └── utils/                  # 🛠️ 유틸리티 (상수, 날짜 포맷)
│   │   └── resources/
│   │       ├── static/                 # 정적 리소스 (CSS, JS, Images)
│   │       ├── templates/              # 뷰 템플릿 (Mustache)
│   │       │   ├── layout/             #    - 공통 레이아웃 (header, footer)
│   │       │   ├── user/               #    - 사용자 관련 뷰
│   │       │   ├── board/              #    - 게시판 관련 뷰
│   │       │   ├── article/            #    - 사진 갤러리 관련 뷰
│   │       │   └── err/                #    - 에러 페이지
│   │       ├── db/
│   │       │   └── data.sql            # 초기 데이터 스크립트
│   │       ├── application.yml         # Spring Boot 공통 설정
│   │       ├── application-dev.yml     # 개발 환경 설정
│   │       └── application-prod.yml    # 운영 환경 설정
│   └── test/                           # 테스트 코드
│       └── java/
│           └── com/tenco/blog/
└── README.md                           # 프로젝트 소개 파일
```