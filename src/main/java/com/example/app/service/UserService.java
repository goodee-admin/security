package com.example.app.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.app.domain.UserDomain;
import com.example.app.mapper.UserMapper;

// 인가/인증과 관련된 메서드들은 UserDetailsService 인터페이스를 구현한 @Service를 사용
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
	
	// 회원가입
	public void addUser(UserDomain userDomain) {
		// DB에 동일한 username이 존재하는지 체크 후 회원가입진행
		if(userMapper.selectUserByname(userDomain.getUsername()) != null) {
			System.out.println("username이 존재합니다: "+userDomain.getUsername());
			return;
		}
		
		userDomain.setPassword(bCryptPasswordEncoder.encode(userDomain.getPassword()));
		userDomain.setRole("ROLE_USER"); // role은 "ROLE_"접두사로 시작하여야 한다.
		userMapper.insertUser(userDomain);
	}
}
