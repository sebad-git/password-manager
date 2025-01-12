package org.uy.sdm.pasman.services;

import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;

import java.util.Collection;

public interface AccountTypeService {

	void addAccountType(AccountTypeDto accountTypeDto);
	Collection<AccountTypeDto> getAccountTypes();

}
