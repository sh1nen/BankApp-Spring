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
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class SavingsTransaction implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String description;
    private String type;
    private String status;
    private Double amount;
    private BigDecimal availableBalance;
    
    @ManyToOne
    @JoinColumn(name = "savings_account_id")
    private SavingsAccount savingsAccount;
    
    public SavingsTransaction(Date date, String description, String type, String status, Double amount, BigDecimal availableBalance, SavingsAccount savingsAccount)
    {
        this.date = date;
        this.description = description;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.availableBalance = availableBalance;
        this.savingsAccount = savingsAccount;
    }
}
