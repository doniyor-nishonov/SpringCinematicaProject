package com.pdp.controller;

import com.pdp.domains.AuthUser;
import com.pdp.dto.LoginDTO;
import com.pdp.dto.SignupDTO;
import com.pdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  19:14
 **/
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute LoginDTO dto) {
        Optional<AuthUser> user = userService.login(dto);
        return "redirect:/home/";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute SignupDTO dto) {
        userService.signup(dto);
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }

    @PostMapping("/logout")
    public String logoutPost() {
        return "redirect:/home/";
    }

    @GetMapping("/failed")
    public String failed() {
        return "auth/failed";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "auth/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public ModelAndView forgotPassword(@RequestParam("phoneNumber") String phoneNumber) {
        ModelAndView model = new ModelAndView();
        Optional<AuthUser> user = userService.checkByPhoneNumber(phoneNumber);
        user.ifPresentOrElse((authUser -> {
            model.addObject("userId", authUser.getId());
            model.setViewName("auth/updatePassword");
        }), () -> model.setViewName("auth/invalidPhoneNumber"));
        return model;
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("newPassword") String password,@RequestParam("userId") Integer userId){
        userService.updatePassword(userId,password);
        return "redirect:/auth/login";
    }
}
