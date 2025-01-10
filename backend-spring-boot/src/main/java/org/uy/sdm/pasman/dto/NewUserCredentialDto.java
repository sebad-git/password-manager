package org.uy.sdm.pasman.dto;

public record NewUserCredentialDto(
	String userName,
	String credentialUser,
	String credentialPassword,
	String name,
	String url
) {}
