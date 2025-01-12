package org.uy.sdm.pasman.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;
import org.uy.sdm.pasman.dto.credentials.CredentialViewDto;
import org.uy.sdm.pasman.dto.credentials.NewUserCredentialDto;
import org.uy.sdm.pasman.dto.credentials.UpdateUserCredentialDto;
import org.uy.sdm.pasman.exceptions.CredentialNotFoundException;
import org.uy.sdm.pasman.model.AccountType;
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.model.UserCredentials;
import org.uy.sdm.pasman.model.VulnerabilityLevel;
import org.uy.sdm.pasman.repos.CredentialRepo;
import org.uy.sdm.pasman.services.CredentialService;
import org.uy.sdm.pasman.services.UserService;

import java.util.Collection;
import java.util.regex.Pattern;

import static org.uy.sdm.pasman.util.crypto.symmetric.SymmetricEncryption.AES;

@Service
@Transactional
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService {

	private final CredentialRepo credentialRepo;
	private final UserService userService;

	private static final Pattern specialCharacterPattern = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
	private static final Pattern digitPattern = Pattern.compile("\\d");
	private static final Pattern uppercasePattern = Pattern.compile("[A-Z]");
	private static final Pattern lowercasePattern = Pattern.compile("[a-z]");

	@Override
	public void addUserCredential(NewUserCredentialDto userPasswordCreateDto) {
		final SecurityUser securityUser = userService.getCurrentUser();
		final String password = securityUser.getPassword();
		final UserCredentials userCredential = new UserCredentials();
		userCredential.setUserId(securityUser.getId());
		userCredential.setUserName(AES().encrypt(password, userPasswordCreateDto.credentialUser()));
		userCredential.setUserPassword(AES().encrypt(password, userPasswordCreateDto.credentialPassword()));
		final AccountType accountType = new AccountType();
		userCredential.setAccountType(new AccountType());
		accountType.setName(userPasswordCreateDto.accountType().name());
		accountType.setUrl(userPasswordCreateDto.accountType().url());
		accountType.setLogo(userPasswordCreateDto.accountType().logo());
		userCredential.setAccountType(accountType);
		userCredential.setVulnerability(getVulnerability(userPasswordCreateDto.credentialPassword()));
		credentialRepo.save(userCredential);
	}

	@Override
	public void updateUserCredential(UpdateUserCredentialDto updateUserCredentialDto) {
		final UserCredentials userCredential = credentialRepo.
			findById(updateUserCredentialDto.id()).orElse(null);
		if(userCredential==null)
			throw new CredentialNotFoundException();
		final SecurityUser securityUser = userService.getCurrentUser();
		final String password = securityUser.getPassword();
		userCredential.setUserName(AES().encrypt(password, updateUserCredentialDto.credentialUser()));
		userCredential.setUserPassword(AES().encrypt(password, updateUserCredentialDto.credentialPassword()));
		userCredential.setVulnerability(getVulnerability(updateUserCredentialDto.credentialPassword()));
		credentialRepo.save(userCredential);
	}

	@Override
	public Collection<CredentialViewDto> getCredentials() {
		final SecurityUser securityUser = userService.getCurrentUser();
		final Collection<UserCredentials> userCredentialsList = credentialRepo.findByUserId(securityUser.getId());
		final String password = securityUser.getPassword();
		return userCredentialsList.stream().map(userCredentials -> new CredentialViewDto(
			userCredentials.getId(),
			AES().decrypt(password, userCredentials.getUserName()),
			AES().decrypt(password, userCredentials.getUserPassword()),
			userCredentials.getVulnerability(),
			new AccountTypeDto(
				userCredentials.getAccountType().getName(),
				userCredentials.getAccountType().getUrl(),
				userCredentials.getAccountType().getLogo()
			)
		)).toList();
	}

	private String getVulnerability(final String password){
		boolean hasSpecialCharacter = specialCharacterPattern.matcher(password).find();
		boolean hasDigit = digitPattern.matcher(password).find();
		boolean hasUppercase = uppercasePattern.matcher(password).find();
		boolean hasLowercase = lowercasePattern.matcher(password).find();
		// Vulnerability Levels
		if (hasSpecialCharacter && hasDigit && hasUppercase && hasLowercase) {
			return VulnerabilityLevel.LOW.getValue();
		} else if (hasSpecialCharacter || hasDigit || hasUppercase || hasLowercase) {
			return VulnerabilityLevel.MEDIUM.getValue();
		} else {
			return VulnerabilityLevel.HIGH.getValue();
		}
	}

}
