package org.uy.sdm.pasman.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uy.sdm.pasman.model.AccountType;

@Repository
public interface AccountTypeRepo extends JpaRepository<AccountType,Long> {}
