package org.uy.sdm.pasman.services.implementation;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uy.sdm.pasman.dto.UserPasswordCreateDto;
import org.uy.sdm.pasman.dto.credentialViewDto;
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.model.UserPasswords;
import org.uy.sdm.pasman.repos.CredentialRepo;
import org.uy.sdm.pasman.repos.UserRepo;
import org.uy.sdm.pasman.services.CredentialService;
import org.uy.sdm.pasman.util.crypto.EncryptionException;
import org.uy.sdm.pasman.util.crypto.symmetric.SymmetricEncryption;

import java.util.Collection;

@Service
@Transactional
public class CredentialServiceImpl implements CredentialService {

	private final CredentialRepo credentialRepo;
	private final UserRepo userRepo;


	public CredentialServiceImpl(
		final CredentialRepo credentialRepo,
		final UserRepo userRepo
	){
		this.credentialRepo = credentialRepo;
		this.userRepo = userRepo;
	}

	@Override
	public void addUserPassword(UserPasswordCreateDto userPasswordCreateDto) throws EncryptionException {
		final SecurityUser securityUser = getUser(userPasswordCreateDto.userName());
		final UserPasswords userPasswords = new UserPasswords();
		final String password = securityUser.getPassword();
		userPasswords.setId(securityUser.getId());
		userPasswords.setUserValue(
			SymmetricEncryption.AES().encrypt(password, userPasswords.getUserValue())
		);
		userPasswords.setPasswordValue(
			SymmetricEncryption.AES().encrypt(password, userPasswords.getPasswordValue())
		);
		credentialRepo.save(userPasswords);
	}

	@Override
	public Collection<credentialViewDto> findByUserName(final String userName) {
		final SecurityUser securityUser = getUser(userName);
		final Collection<UserPasswords> userPasswordsList = credentialRepo.findByUserId(securityUser.getId());
		final String password = securityUser.getPassword();
		return userPasswordsList.stream().map(userPasswords -> {
			try {
				return new credentialViewDto(
					SymmetricEncryption.AES().decrypt(password, userPasswords.getUserValue()),
					SymmetricEncryption.AES().decrypt(password, userPasswords.getUserValue())
				);
			} catch (EncryptionException e) {
				throw new RuntimeException(e);
			}
		}).toList();
	}

	private SecurityUser getUser(final String userName){
		final SecurityUser securityUser = userRepo.findByUsernameIgnoreCase(userName);
		if (securityUser == null)
			throw new UsernameNotFoundException(userName);
		return securityUser;
	}

}
