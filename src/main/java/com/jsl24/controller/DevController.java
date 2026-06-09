package com.jsl24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DevController {

    @GetMapping("/dev/devIndex")
    public String devIndex(Model model) {
        model.addAttribute("content", "dev/devIndex");
        return "layout";
    }
    @GetMapping("/dev/devView")
    public String devView(Model model) {
        model.addAttribute("content", "dev/devView");
        return "layout";
    }
    @GetMapping("/dev/projectView")
    public String projectView(Model model) {
        model.addAttribute("content", "dev/projectView");
        return "layout";
    }

}