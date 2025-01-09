package com.devskiller.tasks.blog.repository;

import com.devskiller.tasks.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	//@Query("SELECT c FROM Comment c WHERE c.postId = :postId")
	Collection<Comment> findCommentsByPostId(Long postId);

}
