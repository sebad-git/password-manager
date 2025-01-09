package org.uy.sdm.pasman.controllers;

import org.uy.sdm.pasman.model.dto.CommentDto;
import org.uy.sdm.pasman.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.uy.sdm.pasman.model.dto.PostDto;
import org.uy.sdm.pasman.services.PostService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	private final CommentService commentService;

	public PostController(
		final PostService postService,
		final CommentService commentService
	) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PostDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}

	@PostMapping(value = "/{postId}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public List<CommentDto> getCommentsForPost(@PathVariable Long postId) {
		return commentService.getCommentsForPost(postId);
	}

	@GetMapping(value = "/{postId}/comments")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentDto> getComments(@PathVariable Long postId) {
		return commentService.getCommentsForPost(postId);
	}
}
