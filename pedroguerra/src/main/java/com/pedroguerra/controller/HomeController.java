package com.pedroguerra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String banana(Model model) {
        model.addAttribute("nome", "Braz");
        return "banana"; // Vai procurar por index.html em /templates
    }
}