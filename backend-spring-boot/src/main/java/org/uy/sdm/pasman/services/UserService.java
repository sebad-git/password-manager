package org.uy.sdm.pasman.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.uy.sdm.pasman.dto.NewUserDto;

public interface UserService {

	void createUser(final NewUserDto userDto);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
