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
		model.addAttribute("content", "company/register :: companyRegisterContent");
		model.addAttribute("pageCss", "personal-register");
		model.addAttribute("pageScript", "personal-register");
		return "layout";
	}

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

	@PostMapping("/signup/company")
	public String signupCompanySubmit(
			@RequestParam(required = false) MultipartFile logoImage,
			RedirectAttributes redirectAttributes) {

		if (logoImage != null && !logoImage.isEmpty() && logoImage.getSize() > 5 * 1024 * 1024) {
			redirectAttributes.addFlashAttribute("infoMessage", "企業ロゴ画像は 5MB 以下にしてください。");
			return "redirect:/signup/company";
		}

		redirectAttributes.addFlashAttribute("infoMessage",
				"現在はデモのため、入力内容はデータベースに保存されていません。");
		return "redirect:/signup/company";
	}
}
