package com.apis.blog.services;

import java.util.List;

import com.apis.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto user);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
