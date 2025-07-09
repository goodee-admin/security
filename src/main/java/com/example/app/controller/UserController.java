package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.UserDomain;
import com.example.app.service.UserService;

@Controller
public class UserController {
	// 생성자 주입
	private UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// 회원가입 폼
	@GetMapping("/addUser")
	public String join() {
		return "addUser";
	}
	// 회원가입 액션
	@PostMapping("/addUserAction")
	public String joinAction(UserDomain user) {
		userService.addUser(user);
		return "redirect:/login";
	}
	
	
	// 로그인 폼
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 로그인 액션
	/*
	@PostMapping("/loginAction")
	public String loginAction() {
		return "";
	}
	*/
}
