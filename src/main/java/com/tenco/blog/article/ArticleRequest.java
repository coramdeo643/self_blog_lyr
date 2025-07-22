package com.tenco.blog.article;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 클라이언트에게 넘어온 데이터를
 * Object로 변화해서 전달하는 DTO 역할을 담당한다
 */
public class ArticleRequest {

	// 게시글 저장 DTO
	@Data
	public static class SaveDTO {
		private String title;
		private String content;
		// username 제거 : 세션에서 가져올 예정

		// (User) <-- toEntity() 호출할 때 세션에서 가져와서 넣어 주면 됨
		public Article toEntity(User user) {
			return Article.builder()
					.title(this.title)
					.user(user)
					.content(this.content)
					.build();
		}

		public void validate() {
			if (title == null || title.trim().isEmpty()) {
				throw new IllegalArgumentException("제목은 필수야");
			}
			if (content == null || content.trim().isEmpty()) {
				throw new IllegalArgumentException("내용은 필수야");
			}
		}
	}

	// 게시글 수정용 DTO 설계
	@Data
	public static class UpdateDTO {
		private String title;
		private String content;

		// toEntity 메서드 안 만들 예정 (더티 체킹 활용)
		// em.find() <--- Board <-- 영속화 <-- 상태값을 변경하면 자동 갱신

		// 유효성 검사
		public void validate() {
			if (title == null || title.trim().isEmpty()) {
				throw new IllegalArgumentException("제목은 필수야");
			}
			if (content == null || content.trim().isEmpty()) {
				throw new IllegalArgumentException("내용은 필수야");
			}
		}
	}

	// 게시글 사진 업로드 전용 DTO 추가
	@Data
	public static class PhotoImageDTO {
		// file 정보가 다 담겨 있게 된다.
		private MultipartFile photoImage;

		public void validate() {
			if (photoImage == null || photoImage.isEmpty()) {
				throw new Exception400("프로필 이미지를 선택해주세요");
			}
			// 파일 크기 검증( 20MB 제한)
			if (photoImage.getSize() > 20 * 1024 * 1024) {
				throw new Exception400("파일 크기는 20MB 이하여야 합니다");
			}
			// 파일 타입 검증 (보안)
			String contentType = photoImage.getContentType();
			if (contentType == null || contentType.startsWith("image/") == false) {
				throw new Exception400("이미지 파일만 업로드 가능합니다");
			}
		}
	}

}
