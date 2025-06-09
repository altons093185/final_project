package com.finalProject.model.dto;

import com.finalProject.model.enums.Role;

import lombok.Data;

@Data
public class UserDto {

	private Integer id;
	private String email;
	private Role role;
	private Boolean isVerified;
}
