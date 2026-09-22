package com.Timepass.ecotrack.service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Timepass.ecotrack.dto.UserDto;
import com.Timepass.ecotrack.entities.Role;
import com.Timepass.ecotrack.entities.User;
import com.Timepass.ecotrack.enums.AppRole;
import com.Timepass.ecotrack.repository.RoleRepository;
import com.Timepass.ecotrack.repository.UserRespository;
import com.Timepass.ecotrack.service.EmailService;
import com.Timepass.ecotrack.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRespository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

	@Override
	public UserDto addUser(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		User user = modelMapper.map(userDto, User.class);
		Role role = roleRepository.findByRoleName(AppRole.ROLE_USER).orElseThrow(()->new RuntimeException("Role not Found"));
		user.setRole(role);
		User savedUser = userRepository.save(user);
		UserDto savedDto = modelMapper.map(savedUser, UserDto.class);
		emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getName());
		return savedDto;
	}

	@Override
	public UserDto addAdmin(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = modelMapper.map(userDto, User.class);
		Role role = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Role not Found"));
		user.setRole(role);
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		if (userDto.getName() != null) user.setName(userDto.getName());
		if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
		if (userDto.getPassword() != null && !userDto.getPassword().isEmpty())
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
		return userRepository.findAll().stream()
				.map(u -> modelMapper.map(u, UserDto.class))
				.toList();
	}

	@Override
	public void deleteUser(Integer id) {
		if (!userRepository.existsById(id)) throw new RuntimeException("User not found");
		userRepository.deleteById(id);
	}

	@Override
	public boolean checkEmailExists(String email) {
		return userRepository.existsByEmail(email);
	}
}
