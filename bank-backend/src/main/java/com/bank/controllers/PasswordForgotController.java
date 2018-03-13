package com.bank.controllers;

import com.bank.domain.Mail;
import com.bank.domain.User;
import com.bank.domain.password.PasswordResetToken;
import com.bank.dtos.PasswordForgotDto;
import com.bank.exceptions.UserNotFoundException;
import com.bank.repositories.PasswordResetTokenRepository;
import com.bank.services.EmailService;
import com.bank.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController
{
    
    private UserService userService;
    private PasswordResetTokenRepository tokenRepository;
    private EmailService emailService;
    
    public PasswordForgotController(UserService userService, PasswordResetTokenRepository tokenRepository, EmailService emailService)
    {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }
    
    @ModelAttribute("forgotPasswordForm")
    public PasswordForgotDto forgotPasswordDto()
    {
        return new PasswordForgotDto();
    }
    
    @GetMapping
    public String displayForgotPasswordPage()
    {
        return "password/forgot-password";
    }
    
    @PostMapping
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
        BindingResult result,
        HttpServletRequest request)
    {
        if(result.hasErrors())
        {
            return "password/forgot-password";
        }

        User user = userService.findByEmail(form.getEmail()).get();
        if(user == null)
        {
            result.rejectValue("email", null, "We could not find an account for that e-mail address.");
            return "password/forgot-password";
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("no-reply@reactivebank.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "https://reactive-bank.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail);

        return "redirect:/forgot-password?success";

    }
}

