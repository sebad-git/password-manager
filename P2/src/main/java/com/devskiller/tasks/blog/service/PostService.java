package com.devskiller.tasks.blog.service;

import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public PostDto getPost(Long id) {
		return postRepository.findById(id)
				.map(post -> new PostDto(post.getTitle(), post.getContent(), post.getCreationDate()))
				.orElse(null);
	}
}
