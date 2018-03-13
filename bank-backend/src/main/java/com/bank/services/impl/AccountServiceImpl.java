package com.bank.services.impl;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.PrimaryTransaction;
import com.bank.domain.SavingsAccount;
import com.bank.domain.SavingsTransaction;
import com.bank.domain.User;
import com.bank.repositories.PrimaryAccountRepository;
import com.bank.repositories.SavingsAccountRepository;
import com.bank.services.AccountService;
import com.bank.services.TransactionService;
import com.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService
{
    
    private static int nextAccountNumber = 123432423;
    
    private SavingsAccountRepository savingsAccountRepository;
    private PrimaryAccountRepository primaryAccountRepository;
    private UserService userService;
    private TransactionService transactionService;
    
    private AccountServiceImpl(SavingsAccountRepository savingsAccountRepository, PrimaryAccountRepository primaryAccountRepository, TransactionService transactionService)
    {
        this.savingsAccountRepository = savingsAccountRepository;
        this.primaryAccountRepository = primaryAccountRepository;
        this.transactionService = transactionService;
    }
    
    @Autowired
    private void setUserService(UserService userService)
    {
        this.userService = userService;
    }
    
    @Override
    public PrimaryAccount createPrimaryAccount()
    {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountNumberGenerator());
        
        return primaryAccountRepository.save(primaryAccount);
    }
    
    @Override
    public SavingsAccount createSavingsAccount()
    {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountNumberGenerator());
        
        return savingsAccountRepository.save(savingsAccount);
    }
    
    @Override
    public void deposit(String accountType, double amount, Principal principal)
    {
        User user = userService.findByUsername(principal.getName()).get();
        
        if(accountType.equalsIgnoreCase("Primary"))
        {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryDepositTransaction(primaryTransaction);
        }
        else if(accountType.equalsIgnoreCase("Savings"))
        {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);
            
            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }
    
    @Override
    public void withdraw(String accountType, double amount, Principal principal)
    {
        User user = userService.findByUsername(principal.getName()).get();
        
        if(accountType.equalsIgnoreCase("Primary"))
        {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
        }
        else if(accountType.equalsIgnoreCase("Savings"))
        {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);
            
            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }
    
    private static int accountNumberGenerator()
    {
        return ++nextAccountNumber;
    }
}
