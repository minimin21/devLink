package com.jsl24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DevController {

    @GetMapping("/dev/devIndex")
    public String projectSearch(Model model) {
        model.addAttribute("content", "dev/devIndex");
        return "layout";
    }
}