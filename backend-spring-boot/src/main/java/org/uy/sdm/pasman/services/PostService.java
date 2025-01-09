package org.uy.sdm.pasman.services;

import org.springframework.stereotype.Service;

import org.uy.sdm.pasman.model.dto.PostDto;
import org.uy.sdm.pasman.repos.PostRepository;
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
