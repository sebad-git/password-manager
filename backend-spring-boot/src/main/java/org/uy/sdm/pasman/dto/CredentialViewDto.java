package org.uy.sdm.pasman.dto;

public record CredentialViewDto(
	Long id,
	String credentialUser,
	String credentialPassword,
	String name,
	String url
) {}
