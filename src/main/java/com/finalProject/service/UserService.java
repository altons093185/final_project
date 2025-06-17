package com.finalProject.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.exception.UserRegisterException;
import com.finalProject.mapper.UserMapper;
import com.finalProject.model.dto.UserCertDto;
import com.finalProject.model.dto.UserDto;
import com.finalProject.model.entity.User;
import com.finalProject.repository.UserRepository;
import com.finalProject.util.Hash;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public UserDto getUser(String email) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		return userOpt.map(userMapper::toDto).orElse(null);
	}

	public User register(String userName, String email, String password) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new UserRegisterException("帳號已存在");
		}
		String salt = Hash.getSalt();
		String passwordHash = Hash.getHash(password, salt);

		User user = new User();
		user.setUserName(userName); // 使用 email 作為預設的使用者名稱
		user.setEmail(email);
		user.setHashPassword(passwordHash);
		user.setHashSalt(salt);
		user.setCreatedAt(LocalDateTime.now());
		return userRepository.save(user);
	}

	public UserCertDto getCurrentUser(HttpSession session) {
		return (UserCertDto) session.getAttribute("userCert");
	}
}
