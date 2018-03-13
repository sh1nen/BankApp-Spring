package com.bank.services.impl;

import com.bank.domain.Mail;
import com.bank.services.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService
{
    private JavaMailSender emailSender;
    
    private SpringTemplateEngine templateEngine;
    
    private EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine templateEngine)
    {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }
    
    public void sendEmail(Mail mail)
    {
        try
        {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
            );
            
            Context context = new Context();
            context.setVariables(mail.getModel());
            String html = templateEngine.process("email/email-template", context);
            
            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());
            
            emailSender.send(message);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
