package com.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Recipient implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String accountNumber;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}

