package com.bank.controllers;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.Recipient;
import com.bank.domain.SavingsAccount;
import com.bank.domain.User;
import com.bank.services.TransactionService;
import com.bank.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransferController
{
    private TransactionService transactionService;
    private UserService userService;
    
    public TransferController(TransactionService transactionService, UserService userService)
    {
        this.transactionService = transactionService;
        this.userService = userService;
    }
    
    @GetMapping("/betweenAccounts")
    public String betweenAccounts(Model model)
    {
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");
        
        return "transfer/betweenAccounts";
    }
    
    @PostMapping("/betweenAccounts")
    public String betweenAccountsPost(
        @ModelAttribute("transferFrom") String transferFrom,
        @ModelAttribute("transferTo") String transferTo,
        @ModelAttribute("amount") String amount,
        Principal principal) throws Exception
    {
        User user = userService.findByUsername(principal.getName()).get();
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();
        
        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);
        return "redirect:/userFront";
    }
    
    @GetMapping("/recipient")
    public String recipient(Model model, Principal principal)
    {
        List<Recipient> recipients = transactionService.findRecipients(principal);
        Recipient recipient = new Recipient();
        model.addAttribute("recipientList", recipients);
        model.addAttribute("recipient", recipient);
        
        return "transfer/recipient";
    }
    
    @PostMapping("/recipient/save")
    public String recipientPost(@ModelAttribute("recipient") Recipient recipient, Principal principal)
    {
        User user = userService.findByUsername(principal.getName()).get();
        recipient.setUser(user);
        
        transactionService.saveRecipient(recipient);
        return "redirect:/transfer/recipient";
    }
    
    @GetMapping("/recipient/edit")
    public String recipientEdit(@RequestParam("recipientName") String recipientName, Model model, Principal principal)
    {
        Recipient recipient = transactionService.findRecipientByName(recipientName);
        List<Recipient> recipients = transactionService.findRecipients(principal);
        
        model.addAttribute("recipientList", recipients);
        model.addAttribute("recipient", recipient);
        
        return "transfer/recipient";
    }
    
    @GetMapping("/recipient/delete")
    @Transactional
    public String recipientDelete(@RequestParam("recipientName") String recipientName, Model model, Principal principal)
    {
        transactionService.deleteRecipientByName(recipientName);
        
        List<Recipient> recipients = transactionService.findRecipients(principal);
        
        Recipient recipient = new Recipient();
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipients);
        
        return "transfer/recipient";
    }
    
    @GetMapping("/toSomeoneElse")
    public String toSomeoneElse(Model model, Principal principal)
    {
        List<Recipient> recipients = transactionService.findRecipients(principal);
        List<User> users = transactionService.findUsers(principal);
        
        model.addAttribute("recipientList", recipients);
        model.addAttribute("userList", users);
        model.addAttribute("accountType", "");
        
        return "transfer/toSomeoneElse";
    }
    
    @PostMapping(value = "/toOtherRecipient")
    public String toOtherRecipient(@ModelAttribute("recipientName") String recipientName,
        @ModelAttribute("accountType") String accountType,
        @ModelAttribute("amount") String amount,
        Principal principal)
    {
        User fromUser = userService.findByUsername(principal.getName()).get();
        
        Recipient recipient = transactionService.findRecipientByName(recipientName);
        transactionService.toSomeoneElse(recipient, accountType, amount, fromUser.getPrimaryAccount(), fromUser.getSavingsAccount());
        
        return "redirect:/userFront";
    }
    
    @PostMapping(value = "/toOtherUser")
    public String toOtherUser(@ModelAttribute("userName") String username,
        @ModelAttribute("userAccountType") String accountType,
        @ModelAttribute("amountUser") String amount,
        Principal principal)
    {
        System.out.println(username + ", " + accountType + ", " + amount);
        User fromUser = userService.findByUsername(principal.getName()).get();
        User toUser = userService.findByUsername(username).get();
        transactionService.toOtherUser(toUser, accountType, amount, fromUser.getPrimaryAccount(), fromUser.getSavingsAccount());
        
        return "redirect:/userFront";
    }
}
