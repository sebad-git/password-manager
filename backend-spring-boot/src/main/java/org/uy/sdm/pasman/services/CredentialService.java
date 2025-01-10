package org.uy.sdm.pasman.services;

import org.uy.sdm.pasman.dto.UserPasswordCreateDto;
import org.uy.sdm.pasman.dto.credentialViewDto;
import org.uy.sdm.pasman.util.crypto.EncryptionException;

import java.util.Collection;

public interface CredentialService {

	void addUserPassword(UserPasswordCreateDto userPasswordCreateDto);
	Collection<credentialViewDto> findByUserName(final String userName);

}
