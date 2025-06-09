package com.finalProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.exception.PasswordInvalidException;
import com.finalProject.exception.UserNotFoundException;
import com.finalProject.model.dto.UserCertDto;
import com.finalProject.model.entity.User;
import com.finalProject.repository.UserRepository;
import com.finalProject.util.Hash;

@Service
public class UserCertService {

	@Autowired
	private UserRepository userRepository;

	public UserCertDto getCert(String email, String password) throws UserNotFoundException, PasswordInvalidException {
		// 1. 是否有此人
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElseThrow(() -> new UserNotFoundException("查無此人"));

		// 2. 密碼 hash 比對
		String passwordHash = Hash.getHash(password, user.getHashSalt());
		if (!passwordHash.equals(user.getHashPassword())) {
			throw new PasswordInvalidException("密碼錯誤");
		}
		// 3. 簽發憑證
		UserCertDto userCert = new UserCertDto(user.getId(), user.getEmail(), user.getRole());
		return userCert;
	}

}
