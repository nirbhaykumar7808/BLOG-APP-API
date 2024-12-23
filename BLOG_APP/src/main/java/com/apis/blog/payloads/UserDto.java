package com.apis.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4 , message = "Username must be greater than 4 characters")
	private String name;
	
	@Email(message = "Email should is Invalid")
	private String email;
	
	@NotEmpty
	@Size(min = 4 ,max = 10, message = "Password must be betweem than 3-10 characters")
	private String password;
	
	@NotNull
	private String about;
}
