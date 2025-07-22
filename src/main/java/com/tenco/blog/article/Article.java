package com.tenco.blog.article;


import com.tenco.blog.comment.Comment;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "article_tb")
@Entity
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // 외래키 컬러명 명시
	private User user;

	@CreationTimestamp
	private Timestamp createdAt;

	@Transient
	private boolean isArticleOwner;

	public boolean isOwner(Long checkUserId) {
		return this.user.getId().equals(checkUserId);
	}

	public String getTime() {
		return MyDateUtil.timestampFormat(createdAt);
	}

	@OrderBy("id DESC") // 정렬 옵션 설정
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.REMOVE)
	List<Comment> comments = new ArrayList<>();


}



