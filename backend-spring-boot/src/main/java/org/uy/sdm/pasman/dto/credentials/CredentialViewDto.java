package org.uy.sdm.pasman.dto.credentials;

import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;

public record CredentialViewDto(
	Long id,
	String credentialUser,
	String credentialPassword,
	String vulnerability,
	AccountTypeDto accountType
) {}
