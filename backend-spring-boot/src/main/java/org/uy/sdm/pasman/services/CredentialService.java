package org.uy.sdm.pasman.services;

import org.uy.sdm.pasman.dto.credentials.CredentialViewDto;
import org.uy.sdm.pasman.dto.credentials.NewUserCredentialDto;

import java.util.Collection;

public interface CredentialService {

	void addUserCredential(NewUserCredentialDto userPasswordCreateDto);
	Collection<CredentialViewDto> getCredentials();

}
