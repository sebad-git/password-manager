package org.uy.sdm.pasman.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;
import org.uy.sdm.pasman.services.AccountTypeService;

import java.util.Collection;

@Controller
@RestController
@RequestMapping(Endpoints.ACCOUNT_TYPE_CONTROLLER)
public class AccountTypeController {

	private final AccountTypeService accountTypeService;

	public AccountTypeController(AccountTypeService accountTypeService) {
		this.accountTypeService = accountTypeService;
	}

	@GetMapping
	public ResponseEntity<Collection<AccountTypeDto>> getAccountTypes() {
		return ResponseEntity.ok(accountTypeService.getAccountTypes());
	}

	@PostMapping()
	public ResponseEntity<String> createAccountType(@RequestBody AccountTypeDto accountTypeDto) {
		accountTypeService.addAccountType(accountTypeDto);
		return ResponseEntity.ok("Account type created successfully.");
	}
}
