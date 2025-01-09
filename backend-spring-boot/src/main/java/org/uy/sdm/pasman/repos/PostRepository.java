package org.uy.sdm.pasman.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import org.uy.sdm.pasman.model.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
