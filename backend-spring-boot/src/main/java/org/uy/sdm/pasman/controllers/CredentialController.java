package org.uy.sdm.pasman.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uy.sdm.pasman.dto.credentials.CredentialViewDto;
import org.uy.sdm.pasman.dto.credentials.NewUserCredentialDto;
import org.uy.sdm.pasman.dto.credentials.UpdateUserCredentialDto;
import org.uy.sdm.pasman.services.CredentialService;

import java.util.Collection;

@Controller
@RestController
@RequestMapping(Endpoints.USER_CREDENTIAL_CONTROLLER)
public class CredentialController {

	private final CredentialService credentialService;

	public CredentialController(CredentialService credentialService) {
		this.credentialService = credentialService;
	}

	@PostMapping("create")
	public ResponseEntity<String> createCredential(@RequestBody NewUserCredentialDto userCredentialDto) {
			credentialService.addUserCredential(userCredentialDto);
			return ResponseEntity.ok("Credential created successfully.");
	}

	@PostMapping("update")
	public ResponseEntity<String> updateCredential(@RequestBody UpdateUserCredentialDto userCredentialDto) {
		credentialService.updateUserCredential(userCredentialDto);
		return ResponseEntity.ok("Credential updated successfully.");
	}

	@GetMapping
	public ResponseEntity<Collection<CredentialViewDto>> getUserCredentials() {
		return ResponseEntity.ok(credentialService.getCredentials());
	}

}
