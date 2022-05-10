package com.nmplus.springbootBoard.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nmplus.springbootBoard.vo.Member;

import lombok.Getter;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
//로그인 후 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다"Security ContextHolder"이라는 키값으로
//오브젝트 => Authentication 타입 객체
//Authentication 안에 User 정보가 있어야 됨
//User 오브젝트타입 => UserDetails 타입 객체

//시큐리티 세션이 가지고 있는 객체 유형은 => Authentication => UserDetatis 타입 
@Getter
public class PrincipalDetail implements UserDetails{ 
	
	private Member member; //컴포지션(구성), 객체를 품고 있는 상속과도 비교된다.
	
	public PrincipalDetail(Member member) {
		this.member=member;
	}
	
	@Override
	public String getPassword() {
		return member.getMemberPwd();
	}

	@Override
	public String getUsername() {
		return member.getMemberId();
	}
	
	//계정이 만료되지 않았는지 리턴하다.(true: 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//계정이 잠겨있는지(true: 풀림)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//비밀번호가 만료됐는지(true: 만료 안 됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화인지(true:활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정이 가지고 있는 권한 목록 리턴(권한의 개수에 따라 반복 돌아야 한다.)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		
		/*
		 * collectors.add(new GrantedAuthority(){
		 * 	@Override
		 *  public String getAuthority(){
		 *  	return "ROLE_"+member.getRole(); //ROLE_USER 의 형태로 리턴
		 *  }
		 * });
		 */
		
		//자바에서는 매개변수로 메소드를 호출하지 못 한다. 
		collectors.add(()->{return "ROLE_"+member.getRole();});
		
		return collectors;
	}
}
