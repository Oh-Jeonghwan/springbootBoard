package com.nmplus.springbootBoard.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nmplus.springbootBoard.service.MemberService;
import com.nmplus.springbootBoard.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/auth/joinForm")
	public String join(Principal principal
					  , Model model){
		if(principal!=null) {
			model.addAttribute("alertMsg","이미 회원이십니다.");
			return "redirect:/";
		}else {
			return "member/joinForm";
		}
		
	}

	@GetMapping("/auth/loginForm")
	public String login() {
		return "member/loginForm";
	}
	
	@GetMapping("/user/info")
	public String memInfo(Principal principal
						, Model model) {
		
		String username = principal.getName();
		
		Member member = memberService.selectMember(username);
		
		model.addAttribute("member", member);
		
		return "member/updateForm";
	}
}
