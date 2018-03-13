package com.bank.controllers;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.SavingsAccount;
import com.bank.domain.User;
import com.bank.domain.security.UserRole;
import com.bank.services.RoleService;
import com.bank.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController
{
    
    private UserService userService;
    private RoleService roleService;
    
    public HomeController(UserService userService, RoleService roleService)
    {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    @GetMapping("/")
    public String home()
    {
        return "redirect:/index";
    }
    
    @GetMapping("/index")
    public String index()
    {
        return "login/index";
    }
    
    @GetMapping("/signup")
    public String singup(Model model)
    {
        User user = new User();
        model.addAttribute("user", user);
        
        return "login/signup";
    }
    
    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") User user, Model model)
    {
        if(userService.findByUsername(user.getUsername()).isPresent() || userService.findByEmail(user.getEmail()).isPresent())
        {
            if(userService.findByEmail(user.getEmail()).isPresent())
            {
                model.addAttribute("emailExists", true);
            }
            if(userService.findByUsername(user.getUsername()).isPresent())
            {
                model.addAttribute("usernameExists", true);
            }
            return "login/signup";
        }
        else
        {
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleService.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            return "redirect:/";
        }
    }
    
    @GetMapping("/userFront")
    public String userFront(Principal principal, Model model)
    {
        User user = userService.findByUsername(principal.getName()).get();
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();
        
        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);
        
        return "overview/userFront";
    }
}
