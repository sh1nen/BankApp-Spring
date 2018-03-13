package com.bank.dtos;

import com.bank.constraint.FieldMatch;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Setter
@Getter
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class PasswordResetDto
{
    
    @NotEmpty
    private String password;
    
    @NotEmpty
    private String confirmPassword;
    
    @NotEmpty
    private String token;
}