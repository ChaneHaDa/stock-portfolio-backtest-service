package com.hada.backtest.jpa.controller;

import com.hada.backtest.jpa.service.SiteUserService;
import com.hada.backtest.jpa.dto.UserCreateForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final SiteUserService siteUserService;

    public UserController(SiteUserService siteUserService){
        this.siteUserService = siteUserService;
    }

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        if (siteUserService.findByUsername(userCreateForm.getUsername())){
            bindingResult.rejectValue("username", "usernameDuplicated",
                    "이미 사용중인 사용자ID입니다.");
            return "signup_form";
        }

        siteUserService.save(userCreateForm.getUsername(), userCreateForm.getPassword1());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
