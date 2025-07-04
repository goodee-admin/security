package com.example.app.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String main(Model model) {
		// 로그인 정보는 스프링시큐리티 관리에 있는 특별한 세션저장소에 저장되기때문에 스프링시큐리티API로 호출해야만 확인가능하다
		// 로그인 되어있는 경우 사용자의 정보가 출력되고,
		// 로그인 되어있지 않는 겨우는 username은 "anonymousUser", role은 "ROLE_ANONYMOUS" 가 출력된다.
		
		// SecurityContextHolder는 스프링시큐리티가 관리하는 세션이다.
		String loginUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String loginRole = auth.getAuthority();
		
		model.addAttribute("loginUsername", loginUsername);
		model.addAttribute("loginRole", loginRole);
		
		return "main";
	}
}
