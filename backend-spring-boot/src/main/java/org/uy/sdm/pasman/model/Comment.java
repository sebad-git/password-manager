package org.uy.sdm.pasman.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	private Long id;

	private String comment;

	@Column
	private String author;

	private LocalDateTime creationDate;
	private Long postId;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
}
