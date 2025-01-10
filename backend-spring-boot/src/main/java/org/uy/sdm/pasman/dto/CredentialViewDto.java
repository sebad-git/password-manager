package org.uy.sdm.pasman.dto;

public record CredentialViewDto(
	String credentialUser,
	String credentialPassword,
	String name,
	String url
) {}
