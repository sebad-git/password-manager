package org.uy.sdm.pasman.services.implementation;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uy.sdm.pasman.dto.UserPasswordCreateDto;
import org.uy.sdm.pasman.dto.UserPasswordViewDto;
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.model.UserPasswords;
import org.uy.sdm.pasman.repos.UserPasswordsRepo;
import org.uy.sdm.pasman.repos.UserRepo;
import org.uy.sdm.pasman.services.UserPasswordsService;

import java.util.Collection;

@Service
@Transactional
public class UserPasswordsServiceImpl implements UserPasswordsService {

	private final UserPasswordsRepo userPasswordsRepo;
	private final UserRepo userRepo;


	public UserPasswordsServiceImpl(
		final UserPasswordsRepo userPasswordsRepo,
		final UserRepo userRepo
	){
		this.userPasswordsRepo = userPasswordsRepo;
		this.userRepo = userRepo;
	}

	@Override
	public boolean addUserPassword(UserPasswordCreateDto userPasswordCreateDto) {
		SecurityUser securityUser = userRepo.findByUsernameIgnoreCase(userPasswordCreateDto.userName());
		if (securityUser == null)
			throw new UsernameNotFoundException(userPasswordCreateDto.userName());
		final UserPasswords userPasswords = new UserPasswords();
		userPasswords.setUserName(securityUser.getUsername());
		//userPasswords.set
		userPasswordsRepo.save(userPasswords);

		return true;
	}

	@Override
	public Collection<UserPasswordViewDto> findByUserId(Long userId) {
		final Collection<UserPasswords> userPasswordsList = userPasswordsRepo.findByUserId(userId);
		return userPasswordsList.stream().map(userPassword -> new UserPasswordViewDto(
			userPassword.getUserValue(),userPassword.getPasswordValue())
		).toList();
	}

}
