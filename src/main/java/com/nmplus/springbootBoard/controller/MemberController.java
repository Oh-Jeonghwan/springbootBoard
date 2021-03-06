package com.nmplus.springbootBoard.controller;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
					  , Model model
					  , RedirectAttributes redirect){
		
		if(principal!=null) {
			redirect.addFlashAttribute("alertMsg","이미 회원이십니다.");
			return "redirect:/";
		}else {
			return "member/joinForm";
		}
		
	}

	@GetMapping("/auth/loginForm")
	public String login(Principal principal
					  , Model model
					  , RedirectAttributes redirect) {
		if(principal!=null) {
			redirect.addFlashAttribute("alertMsg","이미 로그인이 되어있습니다.");
			return "redirect:/";
		}else {
			return "member/loginForm";
		}
		
	}
	
	@GetMapping("/user/info")
	public String memInfo(Principal principal
						, Model model) {
		
		String username = principal.getName();
		
		Member member = memberService.selectMember(username);
		
		model.addAttribute("member", member);
		
		return "member/updateForm";
	}
	

	@PostMapping("/auth/findId")
	public String findId(Model model
						, @RequestParam String memberName
						, @RequestParam String email) {
		Member result = memberService.findId(memberName, email);
		
		if(result!=null) {
			if(result.getStatus().equals("N")) {
				model.addAttribute("result", "이미 탈퇴한 아이디입니다.");
			}else {
				model.addAttribute("result", "조회하신 ID는 "+result.getMemberId()+"입니다.");
			}
		}else {
			model.addAttribute("result", "찾으시는 계정이 없습니다.");
		}
		
		return "member/findResult";
	}
	
	@GetMapping("/logout")
	public String logout(RedirectAttributes redirect
						, HttpServletRequest request
						, HttpServletResponse response)throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();        
		if (auth != null) {            
			new SecurityContextLogoutHandler().logout(request, response, auth);        
		}
		
		redirect.addFlashAttribute("alertMsg","로그아웃 되었습니다.");
		return "redirect:/";
	}
}
