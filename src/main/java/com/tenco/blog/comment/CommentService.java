package com.tenco.blog.comment;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.article.Article;
import com.tenco.blog.article.ArticleJpaRepository;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor // final 키워드를 가진 멤버를 초기화 해
@Service // IoC 대상
public class CommentService {

	private static final Logger log = LoggerFactory.getLogger(CommentService.class);
	private final CommentJpaRepository commentJpaRepository;
	private final ArticleJpaRepository articleJpaRepository;

	@Transactional
	public void save(CommentRequest.SaveDTO saveDTO, User sessionUser) {
		log.info("댓글 저장 서비스 처리 시작 - 게시글 ID {}, 작성자 {}, ",
				saveDTO.getArticleId(), sessionUser.getUsername());

		// 2. 댓글이 달릴 게시글 존재 여부 확인
		Article article = articleJpaRepository.findById(saveDTO.getArticleId())
				.orElseThrow(() -> new Exception404("존재하지 않는 게시글에는 댓글 작성 불가"));
		//3.  준 영속상태
		Comment comment = saveDTO.toEntity(sessionUser, article);
		// 4. 저장 - 정방향 insert 처리
		commentJpaRepository.save(comment);
	}

	@Transactional
	public void deleteById(Long commentId, User sessionUser) {
		log.info("댓글 삭제 서비스 처리 시작 - 댓글 ID {}");

		Comment comment = commentJpaRepository.findById(commentId)
				.orElseThrow(() -> new Exception404("삭제할 댓글이 없습니다"));

		// 현재 로그인한 사용자와 댓글 소유자 확인 더 확인
		if (!comment.isOwner(sessionUser.getId())) {
			throw new Exception403("본인이 작성한 댓글만 삭제 할 수 있음");
		}
		commentJpaRepository.deleteById(commentId);
	}
}
