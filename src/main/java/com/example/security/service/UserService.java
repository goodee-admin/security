package com.example.security.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.dto.User;
import com.example.security.mapper.UserMapper;

@Service
public class UserService {
	// 회원가입, 로그인 등에서 비밀번호를 암호와 하기 위해 BCryptPasswordEncoder Bean을 주입(생성자 주입)
	// SecurityConfig 클래스에 등록되어 있는 Bean
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserMapper userMapper;
	public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userMapper = userMapper;
	}
	
	public void addUser(User user) {
		// DB에 동일한 username이 존재하는지 체크 후 회원가입진행
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER"); // role은 "ROLE_"접두사로 시작하여야 한다.
		userMapper.insertUser(user);
	}
}
