package com.codewithmosh.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //mark a class as a Bean
public class HomeController {
    @RequestMapping("/") // the forward slash represents the root of website
    public String index(Model model) {
        model.addAttribute("name", "Samir");
        return "index";
    }


}
