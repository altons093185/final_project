package com.finalProject.model.dto;

import com.finalProject.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserCertDto {

	private Integer id;
	private String email;
	private Role role;

}
