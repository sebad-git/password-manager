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
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.repos.UserRepo;
import org.uy.sdm.pasman.services.UserService;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void createUser(final NewUserDto userDto) {
		SecurityUser securityUser = new SecurityUser(userDto);
		securityUser.setPassword(passwordEncoder.encode(securityUser.getPassword()));
		userRepo.save(securityUser);
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
