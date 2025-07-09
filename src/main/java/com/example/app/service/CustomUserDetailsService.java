package com.example.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.app.domain.UserDomain;
import com.example.app.dto.CustomUserDetails;
import com.example.app.mapper.UserMapper;

// 인가/인증과 관련된 메서드들은 UserDetailsService 인터페이스를 구현한 @Service를 사용하고
// 그외 사용자와 관련된 CRUD작업은 다른 서비스(ex: class UserService)를 사용 
@Service 
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
		UserDomain user = userMapper.selectUserByname(username);
		if(user == null) {
			// DB에 username이 없으면 UsernameNotFoundException을 발생시켜야 한다.
			throw new UsernameNotFoundException(username+"이 존재하지 않습니다.");
		} 
		return new CustomUserDetails(user); // CustomUserDetails는 빈이 아니기 때문에 생성자를 호출하여
		
	}
	
}
