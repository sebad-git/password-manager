package org.uy.sdm.pasman.dto.credentials;

import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;

public record NewUserCredentialDto(
	String userName,
	String credentialUser,
	String credentialPassword,
	AccountTypeDto accountType
) {}
