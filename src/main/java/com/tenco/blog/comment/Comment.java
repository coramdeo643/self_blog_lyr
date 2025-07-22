package com.tenco.blog.comment;

import com.tenco.blog.article.Article;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Table(name = "comment_tb")
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 500) //기본값 255
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private Article article;

	@CreationTimestamp // 서버 PC 시간 기준
	private Timestamp createdAt;

	@Builder
	public Comment(Long id, String comment, User user, Article article, Timestamp createdAt) {
		this.id = id;
		this.comment = comment;
		this.user = user;
		this.article = article;
		this.createdAt = createdAt;
	}

	@Transient
	private boolean isCommentOwner;

	public boolean isOwner(Long sessionId) {
		return this.user.getId().equals(sessionId);
	}

	public String getTime() {
		return MyDateUtil.timestampFormat(createdAt);
	}

}
