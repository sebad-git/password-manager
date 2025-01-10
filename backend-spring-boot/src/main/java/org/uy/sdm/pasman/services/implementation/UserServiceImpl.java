package org.uy.sdm.pasman.services.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.uy.sdm.pasman.util.jackson.Jackson;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

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

	public SecurityUser getCurrentUser() {
		// Get the authentication object
		final Authentication authentication =
			SecurityContextHolder.getContext().getAuthentication();
		// Check if the user is authenticated
		if (authentication == null || Strings.isEmpty(authentication.getPrincipal().toString())) {
			throw new UsernameNotFoundException("User is not authenticated or found in the application context.");
		}
		try {
			SecurityUser securityUser = Jackson.getObjectMapper().readValue(
				authentication.getPrincipal().toString(),
				SecurityUser.class
			);
			return userRepo.findByUsernameIgnoreCase(securityUser.getUsername());
		} catch (JsonProcessingException jpe) {
			throw new UsernameNotFoundException(jpe.getMessage(),jpe);
		}
	}

}
