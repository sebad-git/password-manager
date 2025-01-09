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
import org.uy.sdm.pasman.dto.PostDto;
import org.uy.sdm.pasman.dto.UserDto;
import org.uy.sdm.pasman.services.UserService;

@Controller
@RestController
@RequestMapping(Endpoints.USER_CONTROLLER)
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity.BodyBuilder createUser(@RequestBody UserDto userDto) {
		userService.createUser(userDto);
		return ResponseEntity.ok();
	}

}
