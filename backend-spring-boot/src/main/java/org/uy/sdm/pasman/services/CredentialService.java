package org.uy.sdm.pasman.services;

import org.uy.sdm.pasman.dto.NewUserCredentialDto;
import org.uy.sdm.pasman.dto.CredentialViewDto;

import java.util.Collection;

public interface CredentialService {

	void addUserCredential(NewUserCredentialDto userPasswordCreateDto);
	Collection<CredentialViewDto> findCredentials();

}
