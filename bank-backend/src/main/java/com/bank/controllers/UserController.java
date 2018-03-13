package com.bank.controllers;

import com.bank.domain.User;
import com.bank.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController
{
    private UserService userService;
    
    private UserController(UserService userService)
    {
        this.userService = userService;
    }
    
    @GetMapping("/profile")
    public String profile(Model model, Principal principal)
    {
        User user = userService.findByUsername(principal.getName()).get();
        
        model.addAttribute("user", user);
        
        return "me/profile";
    }
    
    @PostMapping("/profile")
    public String profilePost(@ModelAttribute("user") User newUser, Model model)
    {
        User user = userService.findByUsername(newUser.getUsername()).get();
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        
        model.addAttribute("user", user);
        userService.save(user);
        
        return "me/profile";
    }
}
