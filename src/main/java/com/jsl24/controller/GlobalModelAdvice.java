package com.jsl24.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {
	
	//ログイン状態の確認
    @ModelAttribute("login")
    public Boolean login(HttpSession session) {
        return session.getAttribute("userId") != null;
    }
    
    //ユーザー権限（ロール）の付与
    @ModelAttribute("role")
    public String role(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null ? role.toString() : "GUEST";
    }

    /** layout.html の条件付き CSS/JS 用（未設定コントローラでは空） */
    @ModelAttribute("pageCss")
    public String pageCss() {
        return "";
    }

    @ModelAttribute("pageScript")
    public String pageScript() {
        return "";
    }
}