package org.uy.sdm.pasman.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uy.sdm.pasman.dto.CredentialViewDto;
import org.uy.sdm.pasman.dto.NewUserCredentialDto;
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.model.UserCredentials;
import org.uy.sdm.pasman.repos.CredentialRepo;
import org.uy.sdm.pasman.services.CredentialService;
import org.uy.sdm.pasman.services.UserService;
import org.uy.sdm.pasman.services.exceptions.CredentialNotFoundException;

import java.util.Collection;

import static org.uy.sdm.pasman.util.crypto.symmetric.SymmetricEncryption.AES;

@Service
@Transactional
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService {

	private final CredentialRepo credentialRepo;
	private final UserService userService;

	@Override
	public void addUserCredential(NewUserCredentialDto userPasswordCreateDto) {
		final SecurityUser securityUser = userService.getCurrentUser();
		final String password = securityUser.getPassword();
		final UserCredentials userCredential = new UserCredentials();
		userCredential.setUserId(securityUser.getId());
		userCredential.setUserName(AES().encrypt(password, userPasswordCreateDto.credentialUser()));
		userCredential.setUserPassword(AES().encrypt(password, userPasswordCreateDto.credentialPassword()));
		userCredential.setName(userPasswordCreateDto.name());
		userCredential.setUrl(userPasswordCreateDto.url());
		credentialRepo.save(userCredential);
	}

	@Override
	public Collection<CredentialViewDto> findCredentials() {
		final SecurityUser securityUser = userService.getCurrentUser();
		final Collection<UserCredentials> userCredentialsList = credentialRepo.findByUserId(securityUser.getId());
		return userCredentialsList.stream().map(userCredentials -> new CredentialViewDto(
			userCredentials.getId(),
			userCredentials.getUserName(),
			userCredentials.getUserPassword(),
			userCredentials.getName(),
			userCredentials.getUrl()
		)).toList();
	}

	@Override
	public CredentialViewDto openCredential(Long credentialId) {
		final SecurityUser securityUser = userService.getCurrentUser();
		final UserCredentials userCredential = this.credentialRepo.findById(credentialId).orElse(null);
		if(userCredential==null)
			throw new CredentialNotFoundException();
		final String password = securityUser.getPassword();
		return new CredentialViewDto(
			userCredential.getId(),
			AES().decrypt(password, userCredential.getUserName()),
			AES().decrypt(password, userCredential.getUserPassword()),
			userCredential.getName(),
			userCredential.getUrl()
		);
	}

}
