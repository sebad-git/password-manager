package org.uy.sdm.pasman.dto.credentials;

import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;

public record NewUserCredentialDto(
	String username,
	String password,
	AccountTypeDto accountType
) {}
