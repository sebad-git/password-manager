package org.uy.sdm.pasman.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uy.sdm.pasman.model.SecurityUser;

@Repository
public interface UserRepo extends JpaRepository<SecurityUser,String> {

	SecurityUser findByUsernameIgnoreCase(String username);
}
