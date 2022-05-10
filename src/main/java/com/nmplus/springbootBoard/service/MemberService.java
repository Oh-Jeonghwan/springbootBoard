package com.nmplus.springbootBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nmplus.springbootBoard.repository.MemberRepository;
import com.nmplus.springbootBoard.vo.Member;
import com.nmplus.springbootBoard.vo.RoleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Member save(Member member) {
		
		String rawPassword = member.getMemberPwd();
		
		String encPassword = encoder.encode(rawPassword);
		
		member.setMemberPwd(encPassword);
		member.setRole(RoleType.USER);
		
		Member memberResult = memberRepository.save(member);
		return memberResult;
	}

	public int idCheck(String id) {
		
		int result = 0;
		Member member = memberRepository.findByMemberId(id);
		
		if(member == null) {
			result = 0;
		}
		else {
			result = 1;
		}
		return result;
	}

	public int emailCheck(String email) {
		int result = 0;
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			result = 0;
		}
		else {
			result = 1;
		}
		return result;
	}

}
