package com.example.travelplan.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {
        if (registered != null) {
            model.addAttribute("registeredMessage",
                    "登録が完了しました。ログインしてください。");
        }
        return "login";  // src/main/resources/templates/login.html
    }
}
