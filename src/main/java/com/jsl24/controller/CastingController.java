package com.jsl24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CastingController {

    @GetMapping("/casting/project")
    public String projectSearch(Model model) {
        model.addAttribute("content", "casting/project :: projectContent");
        return "layout";
    }
    @GetMapping("/casting/company")
    public String companySearch(Model model) {
        model.addAttribute("content", "casting/company :: companyContent");
        return "layout";
    }
}