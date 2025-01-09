package org.uy.sdm.pasman.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uy.sdm.pasman.model.UserPasswords;

import java.util.Collection;

@Repository
public interface UserPasswordsRepo extends JpaRepository<UserPasswords,Long> {

	Collection<UserPasswords> findByUserId(final Long userId);
}
