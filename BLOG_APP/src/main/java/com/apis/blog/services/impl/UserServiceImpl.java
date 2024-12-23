package com.apis.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apis.blog.Entities.Role;
import com.apis.blog.Entities.User;
import com.apis.blog.payloads.UserDto;
import com.apis.blog.repositories.RoleRepo;
import com.apis.blog.repositories.UserRepo;
import com.apis.blog.services.UserService;
import com.apis.blog.exceptions.*;

@Service
public class UserServiceImpl implements UserService {
   
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this .dtoToUser(userDto);
		User savedUser=this.userRepo.save(user); 
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser=this.userRepo.save(user);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users=this.userRepo.findAll();
						
		List<UserDto> userDtos=users.stream().map(user -> this.userToDto(user))
		.collect(Collectors.toList());
		
		return userDtos;
	};

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		this.userRepo.delete(user);
	}

	public User dtoToUser(UserDto userDto) {
		
		 User user=this.modelMapper.map(userDto, User.class);
//		 User user=new User();
//		 user.setId(userDto.getId());
//		 user.setName(userDto.getName());
//		 user.setEmail(userDto.getEmail());
//		 user.setAbout(userDto.getAbout());
//		 user.setPassword(userDto.getPassword());
		 return user;
	}
	
	public UserDto userToDto(User user)  {
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//	    return userDto;
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto,User.class);
		
		//encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role=this.roleRepo.findById(102).get();
		
		user.getRoles().add(role);
		
		User newUser=this.userRepo.save(user);
		
		return this.modelMapper.map(newUser,UserDto.class);
	}
}
