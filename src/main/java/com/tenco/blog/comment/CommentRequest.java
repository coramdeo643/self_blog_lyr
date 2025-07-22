package com.tenco.blog.comment;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.article.Article;
import com.tenco.blog.user.User;
import lombok.Data;

public class CommentRequest {

	@Data
	public static class SaveDTO {
		private Long articleId; // 댓글이 달릴 게시글 ID
		private String comment; // 댓글 내용

		public void validate() {
			if (comment == null || comment.trim().isEmpty()) {
				throw new Exception400("댓글 내용을 입력하시오");
			}
			if (comment.length() > 500) {
				throw new Exception400("댓글은 500자 이내로 작성해주세요");
			}
			if (articleId == null) {
				throw new Exception400("게시글 정보가 필요합니다");
			}
		}

		public Comment toEntity(User sessionUser, Article article) {
			return Comment.builder()
					.comment(comment.trim())
					.user(sessionUser)
					.article(article)
					.build();
		}

	}


}
