package org.uy.sdm.pasman.dto.credentials;

import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;

public record CredentialViewDto(
	Long id,
	String username,
	String password,
	String vulnerability,
	AccountTypeDto accountType
) {}
