package org.uy.sdm.pasman.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uy.sdm.pasman.dto.CredentialViewDto;
import org.uy.sdm.pasman.dto.NewUserCredentialDto;
import org.uy.sdm.pasman.services.CredentialService;

import java.util.Collection;

@Controller
@RestController
@RequestMapping(Endpoints.USER_CREDENTIAL_CONTROLLER)
public class PasswordController {

	private final CredentialService credentialService;

	public PasswordController(CredentialService credentialService) {
		this.credentialService = credentialService;
	}

	@PostMapping
	public ResponseEntity<String> createCredential(@RequestBody NewUserCredentialDto userPasswordCreateDto) {
			credentialService.addUserCredential(userPasswordCreateDto);
			return ResponseEntity.ok("Credential created successfully.");
	}

	@GetMapping
	public ResponseEntity<Collection<CredentialViewDto>> getUserCredentials() {
		return ResponseEntity.ok(credentialService.findCredentials());
	}

	@GetMapping("/{credentialId:[0-9]+}")
	public ResponseEntity<CredentialViewDto> seeUserCredential(@PathVariable Long credentialId) {
		return ResponseEntity.ok(credentialService.openCredential(credentialId));
	}

}
