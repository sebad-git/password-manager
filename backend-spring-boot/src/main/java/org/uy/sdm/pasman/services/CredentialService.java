package org.uy.sdm.pasman.services;

import org.uy.sdm.pasman.dto.credentials.CredentialViewDto;
import org.uy.sdm.pasman.dto.credentials.NewUserCredentialDto;
import org.uy.sdm.pasman.dto.credentials.UpdateUserCredentialDto;

import java.util.Collection;

public interface CredentialService {

	void addUserCredential(NewUserCredentialDto userPasswordCreateDto);
	void updateUserCredential(UpdateUserCredentialDto updateUserCredentialDto);
	Collection<CredentialViewDto> getCredentials();

}
