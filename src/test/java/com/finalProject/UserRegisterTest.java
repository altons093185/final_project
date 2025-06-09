package com.finalProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.finalProject.service.UserService;

@SpringBootTest
public class UserRegisterTest {
	@Autowired
	private UserService userService;

	@Test
	public void testUserAdd() {
		userService.register("test@gmail.com", "1234");
		System.out.println("User add ok!");
	}

	@Test
	public void testGetUser() {
		System.out.println(userService.getUser("test@gmail.com"));
	}
}
