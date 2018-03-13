package com.bank.services;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.SavingsAccount;

import java.security.Principal;

public interface AccountService
{
    PrimaryAccount createPrimaryAccount();
    
    SavingsAccount createSavingsAccount();
    
    void deposit(String accountType, double amount, Principal principal);
    
    void withdraw(String accountType, double amount, Principal principal);
}
