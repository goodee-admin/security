package com.example.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.app.dto.User;
import com.example.app.mapper.UserMapper;

@Service // UserDetails빈은 등록시 Component나 Service를 사용하는데 Mapper를 호출하기 때문에 service를 많이 사용
public class CustomUserDetailsService implements UserDetailsService {
	private UserMapper userMapper;
	public CustomUserDetailsService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	// username과 password를 같이 확인하는 방식이 아닌 username으로 먼저 데이터(User)를 가지고 와서 비밀번호를 확인한다.
	// 입력한 username이 틀리다면 데이터를 가지고 올 수 없다.
	// UserDetailsService는 DB에서 username을 이용하여 먼저 검증 후 인증에 사용할 UserDetails를 반환하는 역활 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.selectUserByname(username);
		if(user != null) {
			return new CustomUserDetails(user); // CustomUserDetails는 빈이 아니기 때문에 생성자를 호출하여 User를 인자로 전달
		}
		return null;
	}
	
}
