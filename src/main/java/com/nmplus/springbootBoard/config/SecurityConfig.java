package com.nmplus.springbootBoard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.nmplus.springbootBoard.config.auth.PrincipalDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration //스프링 컨테이너에서 객체를 관리할 수 있게 빈등록(IoC 관리)
@EnableWebSecurity //요청이 들어올 떄 시큐리티가 먼저 살펴볼 수 있게 필터를 거는 것
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //비밀번호 인코더를 빈등록을 통해 IoC해준다.
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	//해당 password가 뭘로 해시가 되어 회원가입이 되었는지 알아야
	//db에 있는 해시와 비교 가능
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePwd());
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.csrf().disable() //csrf토큰 비활성화(테스트 시에만) => 자바스크립트로 토큰 어떻게 주는지 알아보고 활성화 
			.authorizeRequests() //요청이 들어오면 
				.antMatchers("/auth/**","/js/**", "/css/**", "/image/**","/board/list","/","/logout") // /이런 파일들은
				.permitAll() //누구나 가능
				.anyRequest() //그 외에 다른 인증은
				.authenticated() //인증을 받아야 해 
			.and()
				.formLogin() //로그인 페이지는
				.loginPage("/auth/loginForm") //우리가 지정한 페이지로
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로챈다.
				.defaultSuccessUrl("/")
				.failureUrl("/auth/loginForm")//로그인 성공 또는 실패 시 메시지를 띄울 수 있는지 알아보자
            .and()
            	.logout().permitAll();
	}			
}
