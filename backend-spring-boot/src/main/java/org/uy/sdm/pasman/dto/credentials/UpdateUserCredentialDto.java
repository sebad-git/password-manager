package org.uy.sdm.pasman.dto.credentials;

public record UpdateUserCredentialDto(
	Long id,
	String credentialUser,
	String credentialPassword
) {}
