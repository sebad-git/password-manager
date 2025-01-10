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
		return userCredentialsList.stream().map(userCredentials -> {
			final String password = securityUser.getPassword();
			return new CredentialViewDto(
				AES().decrypt(password, userCredentials.getUserName()),
				AES().decrypt(password, userCredentials.getUserPassword()),
				userCredentials.getName(),
				userCredentials.getUrl()
			);
		}).toList();
	}

}
