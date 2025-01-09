package org.uy.sdm.pasman.services.implementation;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.uy.sdm.pasman.dto.UserConverter;
import org.uy.sdm.pasman.dto.UserDto;
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.repos.UserRepo;
import org.uy.sdm.pasman.services.UserService;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepo userRepo;

	public UserServiceImpl(UserRepo userRepo){
		this.userRepo = userRepo;
	}

	@Override
	public void createUser(final UserDto userDto) {
		SecurityUser securityUser = UserConverter.convert(userDto);
		securityUser.setCreatedDate(LocalDate.now());
		securityUser.setEnabled(true);
		securityUser.setAccountNonLocked(true);
		securityUser.setAccountNonExpired(true);
		securityUser.setCredentialsNonExpired(true);
		this.userRepo.save(securityUser);
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
