package com.example.security;

import com.example.security.controller.AdminController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminController adminController;

    SecurityConfig(AdminController adminController) {
        this.adminController = adminController;
    }
    
    // 1) 인가를 담당한는 "시큐리티필터체인" @Bean 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    	// 2) 접근 경로에 대한 인가 작업
    	
    	// 모든 사용자에게 접근 가능한 경로 설정 - permintAll()
    	// 로그인 사용자 테이블 role컬럼값이 ADMIN에만 허용 - hasRole("ADMIN")
    	// 그외 경로는 로그인 사용자에게만 허용 - anyRequest().authenticated()
        http.authorizeHttpRequests((auth) -> 
        		auth.requestMatchers("/", "/login", "/loginAction", "/WEB-INF/view/**").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated()
                );

        
        // 3) 허용되지 않은 경로 접근시 로그인 페이지로 이동
        
        http.formLogin((auth) -> 
        		auth.loginPage("/login")
        			.loginProcessingUrl("/loginAction"));
        
        
        // 4) CSRF 설정
        
        // CSRF(Cross-Site Request Forgery): 사용자가 의도하지 않은 요청(위조된 입력 폼)을 서버에 보내도록 유도하는 공격입니다.
        // 이런 공격을 방지하기 위해 입력폼에 인증된 CSRF토근값을 같이 보내어 토큰값을 확인 후 응답하는데 Spring Security는 이런 기능을 기본으로 제공한다.
        // 입력폼에서 인증된 토근값을 생성 후 보내야 하기때문에 개발모드에서는 이 기능을 disable 상태에서 개발
        http.csrf((auth) -> auth.disable());
        return http.build();
    }
    
    // 5) Spring Security는 로그인에 사용되는 비밀번호는 암호화된 문자열만 다룬다. 회원가입, 로그인등에 비밀번호 암호화와 관련된 기능을 
    // 	  담당하는 "비크립트패스워드인코드" Bean 등록(회원가입, 로그인구현시 비밀번호 암호화시 호출)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}




