package com.bank.controllers;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.PrimaryTransaction;
import com.bank.domain.SavingsAccount;
import com.bank.domain.SavingsTransaction;
import com.bank.domain.User;
import com.bank.services.AccountService;
import com.bank.services.TransactionService;
import com.bank.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController
{
    
    private UserService userService;
    private AccountService accountService;
    private TransactionService transactionService;
    
    private AccountController(UserService userService, AccountService accountService, TransactionService transactionService)
    {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }
    
    @GetMapping("/primaryAccount")
    public String primaryAccount(Model model, Principal principal)
    {
        List<PrimaryTransaction> primaryTransactions = transactionService.findPrimaryTransactions(principal.getName());
        User user = userService.findByUsername(principal.getName()).get();
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        
        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactions);
        
        return "accounts/primaryAccount";
    }
    
    @GetMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal)
    {
        List<SavingsTransaction> savingsTransactions = transactionService.findSavingsTransactions(principal.getName());
        User user = userService.findByUsername(principal.getName()).get();
        SavingsAccount savingsAccount = user.getSavingsAccount();
        
        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactions);
        
        return "accounts/savingsAccount";
    }
    
    @GetMapping("/deposit")
    public String deposit(Model model)
    {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");
        
        return "overview/deposit";
    }
    
    @PostMapping("/deposit")
    public String depositPost(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal)
    {
        accountService.deposit(accountType, Double.parseDouble(amount), principal);
        
        return "redirect:/userFront";
    }
    
    @GetMapping("/withdraw")
    public String withdraw(Model model)
    {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");
        
        return "overview/withdraw";
    }
    
    @PostMapping("/withdraw")
    public String withdrawPost(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal)
    {
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);
        
        return "redirect:/userFront";
    }
}
