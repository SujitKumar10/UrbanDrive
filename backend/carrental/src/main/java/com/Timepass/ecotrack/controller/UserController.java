package com.Timepass.ecotrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timepass.ecotrack.dto.UserDto;
import com.Timepass.ecotrack.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto)
	{
		UserDto savedUser = userService.addUser(userDto);
		return new ResponseEntity<UserDto>(savedUser,HttpStatus.CREATED);
	}
}
