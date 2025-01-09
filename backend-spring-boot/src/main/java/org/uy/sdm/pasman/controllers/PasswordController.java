package org.uy.sdm.pasman.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.uy.sdm.pasman.dto.UserPasswordCreateDto;
import org.uy.sdm.pasman.dto.credentialViewDto;
import org.uy.sdm.pasman.services.CredentialService;
import org.uy.sdm.pasman.util.crypto.EncryptionException;

import java.util.Collection;

@Controller
@RestController
@RequestMapping(Endpoints.PASSWORD_CONTROLLER)
public class PasswordController {

	private final CredentialService credentialService;

	public PasswordController(CredentialService credentialService) {
		this.credentialService = credentialService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> createCredential(@RequestBody UserPasswordCreateDto userPasswordCreateDto) {
		try {
			credentialService.addUserPassword(userPasswordCreateDto);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (EncryptionException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "/{userName}")
	@ResponseStatus(HttpStatus.OK)
	public Collection<credentialViewDto> getUserPasswords(@PathVariable String userName) {
		return credentialService.findByUserName(userName);
	}

}
