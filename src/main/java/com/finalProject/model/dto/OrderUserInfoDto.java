package com.finalProject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserInfoDto {

	private String name;

	private String phone;

	private String city;

	private String zipCode;

	private String address;

}
