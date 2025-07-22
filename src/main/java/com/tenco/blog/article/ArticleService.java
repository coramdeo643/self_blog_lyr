package com.tenco.blog.article;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.comment.Comment;
import com.tenco.blog.reply.Reply;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service // IoC 대상
@Transactional(readOnly = true)
// 모든 메서드를 일기 전용 트랜잭션으로 실행(findAll, findById 최적화)
// 성능 최적화 (변경 감지 비활성화), 데이터 수정 방지 ()
// 데이터이스 락(lock) 최소화 하여 동시성 성능 개선
public class ArticleService {

    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleJpaRepository articleJpaRepository;

    /**
     * 게시글 저장
     */
    // 메서드 레벨에서의 트랜잭선 선언
    @Transactional // 데이 수정이 필요하는 읽지 전용 설정을 해제하고 쓰기 전용로 변환
    public Article save(ArticleRequest.SaveDTO saveDTO, User sessionUser) {
        // 1. 로그 기록 - 게시글 저장 요청 정보
        // 2. DTO를 Entity로 변환(작성자 정보 포함)
        // 3. 데이터베이스에 게시글 저장
        // 4. 저장 완료 로그 기록
        // 5. 저장된  를 Controller 로 반환
        log.info("게시글 저장 서비스 처리 시작 - 제목 {} , 작성자 {}",
                saveDTO.getTitle(), sessionUser.getUsername());
        Article article = saveDTO.toEntity(sessionUser);
        articleJpaRepository.save(article);
        log.info("게시글 저장 완료 - ID {} , 제목 {}",
                article.getId(), article.getTitle());
        return  article;
    }

    /**
     * 게시글 목록 조회 - 페이징 처리
     */
    public Page<Article> findAll(Pageable pageable) {
        // 1. 로그 기록
        // 2. 데이베이스 게시글 조회
        // 3. 로그 기록
        // 4. 조회된 게시글 목록 반환
        log.info("게시글 조회 서비스 처리 시작");
        Page<Article> articlePage = articleJpaRepository.findAllJoinUser(pageable);
        log.info("게시글 목록 조회 완료 - 토탈 게시글 수 {} 개, 토탈 페이지 {}",
                articlePage.getTotalElements(), articlePage.getTotalPages());
        return articlePage;
    }

    //
    public Article findByIdWithReplies(Long id, User sessionUser) {
        // 1. 게시글 조회
        Article article =  articleJpaRepository.findByIdJoinUser(id).orElseThrow(
                 () -> new Exception404("게시글을 찾을 수 없습니다"));
        // 2. 게시글 작성자 정보 포함해 주어야 함
        // 3. 게시글 소유권 설정(수정/ 삭제버튼 표 시용)
        if(sessionUser != null) {
            boolean isArticleOwner = article.isOwner(sessionUser.getId());
            // 로직 : 메서드를 통해서 게시글 소유자를 확인하고
            // 그 결과 값을  객체에 담아 둔다
            article.setArticleOwner(isArticleOwner);
        }

        // 댓글 정보 <-- 양방향 설정  <--- 댓글 가지옴
        List<Comment> comments = article.getComments();

//         댓글 소유권 설정 (삭제 버튼 표시용)
        if(sessionUser != null) {
//            for(int i = 0; i < replies.size(); i++) { }
            comments.forEach(comment -> {
                boolean isCommentOwner = comment.isOwner(sessionUser.getId());
                comment.setCommentOwner(isCommentOwner);
            });
        }
        return article;
    }


    /**
     * 게시글 상세 조회
     */
    public Article findById(Long id) {
        // 1. 로그 기록
        // 2. 데이터 베스에서 해당  id 로 조회 -  WHERE
        // 3. 게시글이 없다면 404 에러 처리
        // 4. 조회 성공시 로그 기록
        // 5. 조회된 게시글 반환
        log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
        Article article = articleJpaRepository.findByIdJoinUser(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("게시글을 찾을 수 없습니다");
        });
        log.info("게시글 상세 조회 완료 - 제목 {}", article.getTitle());
        return article;
    }

    /**
     *  게시글 수정(권한 체크 포함)
     */
    @Transactional
    public Article updateById(Long id, ArticleRequest.UpdateDTO updateDTO,
                              User sessionUser) {
        // 1. 로그 기록
        // 2. 수정하려는 게시글 조회
        // 3. 권한 체크
        // 4. 권한이 없다면 403 예외 발생
        // 5.  엔티티에 상태값 변경 (더티 체팅)
        // 6. 로그 기록 - 수정 완료
        // 7. 수정된 게시글 반환
        log.info("게시글 수정 서비스 시작 - 게시글 ID {}", id);
        Article article = articleJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("해당 게시글이 존재하지 않습니다");
        });

        if(!article.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 수정 가능");
        }

        article.setTitle(updateDTO.getTitle()); // 필드값 상태 변경
        article.setContent(updateDTO.getContent()); // 필드값 상태 변경
        // 더티 체킹
        log.info("게시글 수정 완료 - 게시글 ID {}, 게시글 제목 {}", id, article.getTitle());
        return article;
    }

    /**
     * 게시글 삭제 (권한 체크)
     */
    @Transactional
    public void deleteById(Long id, User sessionUser) {
        // 1. 로그 기록
        // 2. 삭제 하려는 게시글 조회
        // 3. 권한 체크
        // 4. 권한이 없으면 403 예외 처리
        // 5. 데이터 베이스 삭제 처리
        // 6. 삭제 완료 로그 기록
        log.info("게시글 삭제 서비스 시작 - ID {}", id);
        Article article = articleJpaRepository.findById(id).orElseThrow(() -> {
            return new Exception404("삭제하려는 게시글이 없습니다");
        });
        if(!article.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        articleJpaRepository.deleteById(id);
    }

    /**
     *  게시글 소유자 확인 (수정 화면 요청 확인용)
     */
    public void checkArticleOwner(Long articleId, Long userId) {
        Article article = findById(articleId);
        if(!article.isOwner(userId)) {
            throw new Exception403("본인 게시글만 수정할 수 있습니다.");
        }
    }

}
