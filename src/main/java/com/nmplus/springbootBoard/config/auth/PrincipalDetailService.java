package com.nmplus.springbootBoard.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nmplus.springbootBoard.repository.MemberRepository;
import com.nmplus.springbootBoard.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private MemberRepository memberRepository;
	
	//스프링이 로그인 요청을 가로챌 때, userName, passowrd 변수 2개를 가로채는데
	//password 부분처리는 알아서 함
	//username이 DB에 있는지만 확인해주면 됨
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException{
		
		String status = "Y";
		
		Member principal = memberRepository.findByMemberIdAndStatus(memberId,status)
				.orElseThrow(()->{
					return new UsernameNotFoundException("아이디 또는 비밀번호를 찾을 수 없습니다.");
				});
		
		log.debug("프린시펄은???? "+principal);
		log.debug("principal " + principal);
		return new PrincipalDetail(principal); //시큐리티의 세션에 유저 정보가 userDetatils타임으로 저장이 됨
		
		//이 과정을 통해 ID: user, PW: 콘솔창에 뜨던 것 외에 db에서 비교해와서 로그인을 가능하게 함
	}
}
