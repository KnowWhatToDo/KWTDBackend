package com.KWTD.register.mentee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

// @RestController
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name,
            Model model) {
        // return name to the template
        model.addAttribute("name", name);
        return "hello";

    }

}
