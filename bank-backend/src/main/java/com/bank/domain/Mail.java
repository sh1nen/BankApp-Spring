package com.bank.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Mail
{
    private String from;
    private String to;
    private String subject;
    private Map<String, Object> model;
    
}
