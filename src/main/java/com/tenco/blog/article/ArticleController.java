package com.tenco.blog.article;

import com.tenco.blog._core.common.PageLink;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {

	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
	private final ArticleService articleService;
	private final PhotoUploadService photoUploadService;

	/**
	 * 게시글 수정 화면 요청
	 */
	@GetMapping("/article/{id}/update-form")
	public String updateForm(@PathVariable(name = "id") Long articleId,
							 HttpServletRequest request, HttpSession session) {
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		articleService.checkArticleOwner(articleId, sessionUser.getId());
		request.setAttribute("article", articleService.findById(articleId));
		return "article/update-form";
	}


	@PostMapping("/article/{id}/update-form")
	public String update(@PathVariable(name = "id") Long articleId,
						 ArticleRequest.UpdateDTO reqDTO,
						 HttpSession session) {
		// 1. 인증 검사
		// 2. 데이터 유효성 검사
		// 3. 수정 요청 위임
		// 4. 리다이렉트 처리
		reqDTO.validate();
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		articleService.updateById(articleId, reqDTO, sessionUser);

		return "redirect:/article/" + articleId;
	}

	@PostMapping("/article/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id, HttpSession session) {
		// 1. 인증 검사
		// 2. 세션에서 로그인 한 사용자 정보 추출
		// 3. 서비스 위임
		// 4. 메인 페이지로 리다이렉트 처리
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		articleService.deleteById(id, sessionUser);
		return "redirect:/article/list";
	}

	@GetMapping("/article/save-form")
	public String saveForm() {
		return "article/save-form";
	}

	@PostMapping("/article/save")
	public String save(ArticleRequest.SaveDTO reqDTO, HttpSession session) {
		// 1. 인증검사
		// 2. 유효성 검사
		// 3. 서비스 계층 위임
		reqDTO.validate();
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		articleService.save(reqDTO, sessionUser);
		return "redirect:/article/list";
	}


	@GetMapping("/article/list")
	public String index(Model model,
						@RequestParam(name = "page", defaultValue = "1") int page,
						@RequestParam(name = "size", defaultValue = "3") int size) {

		//                   현재 페이지 번호 , 한 페이지당 게시글 갯수
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
		Page<Article> articlePage = articleService.findAll(pageable);

		// 페이지 네비게이션 용 데이터 준비
		List<PageLink> pageLinks = new ArrayList<>();
		for (int i = 0; i < articlePage.getTotalPages(); i++) {
			pageLinks.add(new PageLink(i, i + 1, i == articlePage.getNumber()));
		}

		Integer previousPageNumber = articlePage.hasPrevious() ? articlePage.getNumber() : null;
		Integer nextPageNumber = articlePage.hasNext() ? articlePage.getNumber() + 2 : null;

		// 뷰 화면에 데이터 전달
		model.addAttribute("articlePage", articlePage);
		// 페이지 네비게이션에 사용할 번호 링크 리스트
		model.addAttribute("pageLinks", pageLinks);
		// 이전 페이지 번호 (없으면 null)
		model.addAttribute("previousPageNumber", previousPageNumber);
		// 다음 페이지 번호 (없으면 null)
		model.addAttribute("nextPageNumber", nextPageNumber);
		return "article/list";
	}

	@GetMapping("/article/{id}")
	public String detail(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		Article article = articleService.findByIdWithReplies(id, sessionUser);
		model.addAttribute("article", article);
		return "article/detail";
	}

	// 게시글 사진 업로드
	@PostMapping("/article/{id}/upload-photo")
	public String uploadPhoto(
			@PathVariable(name = "id") Long articleId,
			@RequestParam(name = "photoImage") MultipartFile multipartFile,
			HttpSession session) {
		ArticleRequest.PhotoImageDTO photoImageDTO = new ArticleRequest.PhotoImageDTO();
		photoImageDTO.setPhotoImage(multipartFile);
		photoImageDTO.validate();
		articleService.uploadPhotoImage(articleId, multipartFile);
		return "redirect:/article/" + articleId + "/update-form";
	}

	// 게시글 사진 삭제
	@PostMapping("/article/{id}/delete-photo")
	public String deletePhoto(
			@PathVariable(name = "id") Long articleId,
			HttpSession session) {
		articleService.deletePhotoImage(articleId);
		return "redirect:/article/" + articleId + "/update-form";
	}


}
