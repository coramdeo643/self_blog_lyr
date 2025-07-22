package com.tenco.blog.comment;

import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CommentController {

	private final CommentService commentService;

	// 댓글 저장 기능 요청
	@PostMapping("/comment/save")
	public String save(CommentRequest.SaveDTO saveDTO, HttpSession session) {
		// 인증 검사 (인터셉터에서 처리)
		// 유효성 검사
		saveDTO.validate();
		// sessionUser
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		commentService.save(saveDTO, sessionUser);
		return "redirect:/article/" + saveDTO.getArticleId();
	}

	// 댓글 삭제 기능 요청
	@PostMapping("/comment/{id}/delete")
	public String delete(@PathVariable(name = "id") Long commentId,
						 @RequestParam(name = "articleId") Long articleId,
						 HttpSession session) {
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		commentService.deleteById(commentId, sessionUser);

		return "redirect:/article/" + articleId;
	}


}
