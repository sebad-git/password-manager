package org.uy.sdm.pasman.services.implementation;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uy.sdm.pasman.dto.NewUserDto;
import org.uy.sdm.pasman.dto.UserDataDto;
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.repos.UserRepo;
import org.uy.sdm.pasman.services.UserService;

import java.time.LocalDate;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDataDto createUser(final NewUserDto userDto) {
		SecurityUser securityUser = new SecurityUser(userDto);
		securityUser.setPassword(passwordEncoder.encode(securityUser.getPassword()));
		securityUser = userRepo.save(securityUser);
		return new UserDataDto(
			securityUser.getUsername(),
			securityUser.getLastName(),
			securityUser.getFirstName(),
			securityUser.getMiddleName()
		);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (Strings.isEmpty(username))
			throw new UsernameNotFoundException(username);
		SecurityUser user = this.userRepo.findByUsernameIgnoreCase(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return user;
	}

}
