package com.bank.resources;

import com.bank.domain.PrimaryTransaction;
import com.bank.domain.SavingsTransaction;
import com.bank.domain.User;
import com.bank.services.TransactionService;
import com.bank.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserResource
{
    UserService userService;
    TransactionService transactionService;
    
    public UserResource(UserService userService, TransactionService transactionService)
    {
        this.userService = userService;
        this.transactionService = transactionService;
    }
    
    @GetMapping("/all")
    public List<User> userList()
    {
        return userService.findUserList();
    }
    
    @GetMapping("/primary/transaction")
    public List<PrimaryTransaction> retrievePrimaryTransactionList(@RequestParam("username") String username)
    {
        return transactionService.findPrimaryTransactions(username);
    }
    
    @GetMapping("/savings/transaction")
    public List<SavingsTransaction> retrieveSavingsTransactionList(@RequestParam("username") String username)
    {
        return transactionService.findSavingsTransactions(username);
    }
    
    @RequestMapping("/{username}/enable")
    public void enableUser(@PathVariable String username)
    {
        userService.enableUser(username);
    }
    
    @RequestMapping("/{username}/disable")
    public void disableUser(@PathVariable String username)
    {
        userService.disableUser(username);
    }
}
