package com.jsl24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	//index
	@GetMapping({"/","/index.html"})
	public String index(HttpSession session, Model model) {

		Object user = session.getAttribute("userId"); 
	    model.addAttribute("login", user != null);

	    model.addAttribute("content", "pages/index :: indexContent");

	    return "layout";
	}
	
	//ログイン
	@PostMapping("/login")
	public String loginProcess(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session) {

		session.setAttribute("userId", id);

		String role;

		if (id.startsWith("admin")) {
		    role = "ADMIN";
		} else if (id.startsWith("company")) {
		    role = "COMPANY";
		} else {
		    role = "USER";
		}

		session.setAttribute("role", role);

	    return "redirect:/";
	}
	
	//ログアウト
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
	    session.invalidate();
	    
	    return "redirect:/";
	}
	
	//管理者専用メニューセキュリティ
	@GetMapping("/admin/dashboard")
	public String adminPage(HttpSession session) {
	    String role = (String) session.getAttribute("role");
	    if (!"ADMIN".equals(role)) {
	        return "redirect:/"; 
	    }
	    return "admin/dashboard";
	}
	
}
