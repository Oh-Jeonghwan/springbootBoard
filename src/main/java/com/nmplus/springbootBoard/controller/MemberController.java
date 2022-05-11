package com.nmplus.springbootBoard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nmplus.springbootBoard.service.MemberService;
import com.nmplus.springbootBoard.vo.Member;
import com.nmplus.springbootBoard.vo.RoleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	@GetMapping("/auth/joinForm")
	public String join(){
		return "member/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String login() {
		return "member/loginForm";
	}

}
