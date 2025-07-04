package com.example.app.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.app.dto.User;

// UserDetails 인터페이스를 구현한 클래스에 인증에 사용된 DTO타입을 주입
public class CustomUserDetails implements UserDetails {
	private User user; // 인증에 사용되는 DTO타입
	public CustomUserDetails(User user) { // CustomUserDetails는 빈이 아니기 때문에 생성자가 코드상에스 호출되어야 함
		this.user = user;
	}
	
	// password와 username은 문자열로 반환하면 되는데
	// role의 경우는 아주 복잡한 형태의 타입(Collection<GrantedAuthority>)으로 반한하여야 한다.(보안 때문에....)
	// role
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> roles = new ArrayList<>();
		
		// GrantedAuthority가 인터페이스라 부모생성자를 이용하여 익명객체를 생성하여야 한다.
		GrantedAuthority ga = new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		};
		
		roles.add(ga);
		return roles;
	}
	
	// password
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}
	
	// username
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUsername();
	}

}
