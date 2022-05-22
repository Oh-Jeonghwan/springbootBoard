package com.nmplus.springbootBoard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class HomeController {
	@GetMapping
	public String home(Model model
			  		 , HttpServletRequest request) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		
		String alertMsg = null;
		if(inputFlashMap != null) {
			alertMsg = (String)inputFlashMap.get("alertMsg");
		}
		
		model.addAttribute("alertMsg", alertMsg);
		return "home";
	}

}
