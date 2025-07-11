package com.example.app.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    // 0) 인가를 담당한는 "시큐리티필터체인" @Bean 등록
    @Bean
    // ★★★★★ filterChain메서드는 SpringSecurity가 제공한는 인가/인증과 관련된 여러설정정보를 가지는 HttpSecurity타입을 인자로 받음 
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
    	
    	// 1) CSRF 설정
        
        // CSRF(Cross-Site Request Forgery): 사용자가 의도하지 않은 요청(위조된 입력 폼)을 서버에 보내도록 유도하는 공격입니다.
        // 이런 공격을 방지하기 위해 입력폼에 인증된 CSRF토근값을 같이 보내어 토큰값을 확인 후 응답하는데 Spring Security는 이런 기능을 기본으로 제공한다.
        // 입력폼에서 인증된 토근값을 생성 후 보내야 하기때문에 개발모드에서는 이 기능을 disable 상태에서 개발
    	// ★★★★★ HttpSecurity객체를 랩핑하여 csrf관련된 정보를 저장하는 CsrfConfigurer객체값을 설정
    	httpSecurity.csrf((csrfConfigurer) -> csrfConfigurer.disable());
    	
    	
    	// 2) 접근 경로에 대한 인가 작업
    	
    	// 모든 사용자에게 접근 가능한 경로 설정 - permintAll()
    	// 사용자 테이블 role컬럼값이 "ROLE_ADMIN"에만 허용 - hasRole("ADMIN")
    	// 사용자 테이블 role컬럼값이 "ROLE_USER"에만 허용 - hasRole("USER")
    	// 그외 경로는 로그인 사용자에게만 허용 - anyRequest().authenticated()
        
    	// ★★★★★ HttpSecurity객체가 가진 정보중 
    	// ★★★★★ 인가목록과 관련된 정보를 가진 AuthorizationManagerRequestMatcherRegistry객체값을 설정
    	httpSecurity.authorizeHttpRequests((requestMatcherRegistry) -> 
    			requestMatcherRegistry.requestMatchers("/","/WEB-INF/view/**").permitAll()
				        			.requestMatchers("/addUser", "/addUserAction", "/login", "/loginAction").permitAll()
				        			// /user/아래 경로는 ROLE_ADMIN, ROLE_USER 접근가능
				        			.requestMatchers("/user/**").hasRole("ADMIN")
				        			.requestMatchers("/user/**").hasRole("USER")
				        			// /admin/아래 경로는 ROLE_ADMIN만 접근가능 
				                    .requestMatchers("/admin/**").hasRole("ADMIN")
				                    .anyRequest().authenticated()
                );

        
        // 3) 허용되지 않은 경로 접근시 로그인 페이지로 이동
        // ★★★★★ HttpSecurity객체를 랩핑하여 로그인 프로세스와 관련된 정보를 저장한는 FormLoginConfigurer객체값을 설정
    	httpSecurity.formLogin((formLoginConfigurer) -> 
    			formLoginConfigurer.loginPage("/login")
    			.loginProcessingUrl("/loginAction")
    			.successHandler(loginSuccessHandler())
    			.failureHandler(loginFailureHandler()));
        			// 로그인 액션을 가로채서 스프링시큐리티에 위임
        			// DB 로그인 인증방식은 스프링시큐리티에서 제공하는 UserDetails와 UserDetailsService Bean을 커스트마이징하여 사용하여 검증
        			// UserDetailsService : Repository(Mapper)와 통신하여 스프링시큐리티가 사용하는 DTO를 반환하는 Service 역활을 하는 빈
        			// UserDetails : User(DTO)를 스프링시큐리티 인증/인가에 사용가능한 DTO형태 
        			// 스프링시큐리티가 인증/인가를 진행하기 위해서는 정해진 형태의 DTO(UserDetails)를 넘겨줘야한다.
        			// 로그인 정보는 스프링시큐리티 관리에 있는 특별한 세션저장소에 저장되기때문에 스프링시큐리티API로 호출해야만 확인가능하다. -> MainController에서 코드 확인!
        			
        
        // 4) 로그아웃
    	// ★★★★★ HttpSecurity객체를 랩핑하여 로그아웃 프로세스와 관련된 정보를 저장한는 LogoutConfigurer객체값을 설정
    	httpSecurity.logout((logoutConfigurer) -> 
    			logoutConfigurer.logoutUrl("/logout") // 로그아웃 요청 url
							  .invalidateHttpSession(true) // 세션정보 삭제
		     				  .logoutSuccessUrl("/login")); // 로그아웃 성공시 리다이렉션 url		  	
        
        return httpSecurity.build();
    }
    
	/* 로그인 성공시 */
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

				// 로그인 성공후 추가적으로 진행되어야 할 코드가 있다면 구현
				System.out.println("로그인 성공");
				response.sendRedirect("/");
			}
		};
	}
	
	/* 로그인 실패시 */
	public AuthenticationFailureHandler loginFailureHandler() {
		return new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				// 로그인 성공후 추가적으로 진행되어야 할 코드가 있다면 구현
				System.out.println("로그인 실패");
				response.sendRedirect("/login");
			}
		};
	}
    
    
    
    // 5) Spring Security는 로그인에 사용되는 비밀번호는 암호화된 문자열만 다룬다. 회원가입, 로그인등에 비밀번호 암호화와 관련된 기능을 
    // 	  담당하는 "비크립트패스워드인코드" Bean 등록(회원가입, 로그인구현시 비밀번호 암호화시 호출)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}




