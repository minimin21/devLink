package com.jsl24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

	@GetMapping("/signup")
	public String signupType(Model model) {
		model.addAttribute("content", "personal/signup-type :: signupTypeContent");
		model.addAttribute("pageCss", "personal-register");
		return "layout";
	}

	@GetMapping("/signup/personal")
	public String signupPersonal(Model model) {
		model.addAttribute("content", "personal/register :: personalRegisterContent");
		model.addAttribute("pageCss", "personal-register");
		model.addAttribute("pageScript", "personal-register");
		return "layout";
	}

	@GetMapping("/signup/company")
	public String signupCompany(Model model) {
		model.addAttribute("content", "company/signup-placeholder :: companySignupPlaceholderContent");
		model.addAttribute("pageCss", "personal-register");
		return "layout";
	}

	/**
	 * DB 未接続のため入力は保存しません。接続後にサービス層へ委譲してください。
	 */
	@PostMapping("/signup/personal")
	public String signupPersonalSubmit(
			@RequestParam(required = false) MultipartFile profileImage,
			RedirectAttributes redirectAttributes) {

		if (profileImage != null && !profileImage.isEmpty() && profileImage.getSize() > 5 * 1024 * 1024) {
			redirectAttributes.addFlashAttribute("infoMessage", "プロフィール画像は 5MB 以下にしてください。");
			return "redirect:/signup/personal";
		}

		redirectAttributes.addFlashAttribute("infoMessage",
				"現在はデモのため、入力内容はデータベースに保存されていません。");
		return "redirect:/signup/personal";
	}
}
