package org.uy.sdm.pasman.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uy.sdm.pasman.dto.NewUserDto;
import org.uy.sdm.pasman.services.UserService;

@Controller
@RestController
@RequestMapping(Endpoints.USER_CONTROLLER)
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("create")
	public ResponseEntity<NewUserDto> createUser(@RequestBody NewUserDto userDto) {
		return ResponseEntity.ok(userService.createUser(userDto));
	}

}
