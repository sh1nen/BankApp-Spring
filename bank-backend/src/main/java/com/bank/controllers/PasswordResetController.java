package com.bank.controllers;

import com.bank.domain.User;
import com.bank.domain.password.PasswordResetToken;
import com.bank.dtos.PasswordResetDto;
import com.bank.repositories.PasswordResetTokenRepository;
import com.bank.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController
{
    
    private UserService userService;
    private PasswordResetTokenRepository tokenRepository;
    private BCryptPasswordEncoder passwordEncoder;
    
    public PasswordResetController(UserService userService, PasswordResetTokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder)
    {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @ModelAttribute("passwordResetForm")
    public PasswordResetDto passwordReset()
    {
        return new PasswordResetDto();
    }
    
    @GetMapping
    public String displayResetPasswordPage(@RequestParam(required = false) String token,
        Model model)
    {
        
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if(resetToken == null)
        {
            model.addAttribute("error", "Could not find password reset token.");
        }
        else if(resetToken.isExpired())
        {
            model.addAttribute("error", "Token has expired, please request a new password reset.");
        }
        else
        {
            model.addAttribute("token", resetToken.getToken());
        }
        
        return "password/reset-password";
    }
    
    @PostMapping
    @Transactional
    public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form,
        BindingResult result,
        RedirectAttributes redirectAttributes)
    {
        
        if(result.hasErrors())
        {
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return "redirect:/reset-password?token=" + form.getToken();
        }
        
        PasswordResetToken token = tokenRepository.findByToken(form.getToken());
        User user = token.getUser();
        String updatedPassword = passwordEncoder.encode(form.getPassword());
        userService.updatePassword(updatedPassword, user.getUserId());
        tokenRepository.delete(token);
        
        return "redirect:/login?resetSuccess";
    }
    
}
