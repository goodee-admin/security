package com.example.app.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.app.domain.UserDomain;

// SpringSecurity가 인가/인증에 사용하는 UserDetails 인터페이스를 구현한 DTO타입
// UserDetails 인터페이스를 구현한 클래스에 인증에 사용된 DTO타입을 주입
public class CustomUserDetails implements UserDetails {
	private UserDomain userDomain; // 인증에 사용되는 Domain타입(DB 테이블과 동일한 타입)
	// 생성자 주입
	public CustomUserDetails(UserDomain userDomain) { 
		this.userDomain = userDomain;
	}
	
	// password와 username은 문자열로 반환하면 되는데
	// role의 경우는 아주 복잡한 형태의 타입(Collection<GrantedAuthority>)으로 반한하여야 한다.(보안 때문에....)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> roles = new ArrayList<>();
		
		// GrantedAuthority가 인터페이스라 부모생성자를 이용하여 익명객체를 생성하여야 한다.
		GrantedAuthority grantedAuthority = new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return userDomain.getRole();
			}
		};
		
		roles.add(grantedAuthority);
		return roles;
	}
	
	// password
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.userDomain.getPassword();
	}
	
	// username
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userDomain.getUsername();
	}

}
