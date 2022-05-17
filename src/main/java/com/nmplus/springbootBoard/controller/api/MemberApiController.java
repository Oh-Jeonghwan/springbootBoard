package com.nmplus.springbootBoard.controller.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nmplus.springbootBoard.service.MemberService;
import com.nmplus.springbootBoard.vo.Member;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class MemberApiController {
	
	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/auth/joinProc")
	@ResponseBody
	public Member join(@RequestBody Member member) {
		return memberService.save(member);
	}
	
	@PostMapping("/auth/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam String id) {
		return memberService.idCheck(id);
	}

	@PostMapping("/auth/emailCheck")
	@ResponseBody
	public int emailCheck(@RequestParam String email) {
		return memberService.emailCheck(email);
	}
	
	@PutMapping("/user/memberEdit")
	@ResponseBody
	public int memberEdit (@RequestBody Member member
							, Principal principal) {
		int result = memberService.memberEdit(principal, member);
		return result;
	}
}
