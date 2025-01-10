package org.uy.sdm.pasman.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uy.sdm.pasman.model.UserCredentials;

import java.util.Collection;

@Repository
public interface CredentialRepo extends JpaRepository<UserCredentials,Long> {

	Collection<UserCredentials> findByUserId(final Long userId);
}
