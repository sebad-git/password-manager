package org.uy.sdm.pasman.services;

import org.uy.sdm.pasman.dto.UserPasswordCreateDto;
import org.uy.sdm.pasman.dto.UserPasswordViewDto;
import org.uy.sdm.pasman.model.UserPasswords;

import java.util.Collection;

public interface UserPasswordsService {

	boolean addUserPassword(UserPasswordCreateDto userPasswordCreateDto);
	Collection<UserPasswordViewDto> findByUserId(Long userId);

}
