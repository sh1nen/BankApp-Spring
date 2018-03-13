package com.bank.domain;

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
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Appointment implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String location;
    private String description;
    private boolean confirmed;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
