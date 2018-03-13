package com.bank.services.impl;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.PrimaryTransaction;
import com.bank.domain.Recipient;
import com.bank.domain.SavingsAccount;
import com.bank.domain.SavingsTransaction;
import com.bank.domain.User;
import com.bank.repositories.PrimaryAccountRepository;
import com.bank.repositories.PrimaryTransactionRepository;
import com.bank.repositories.RecipientRepository;
import com.bank.repositories.SavingsAccountRepository;
import com.bank.repositories.SavingsTransactionRepository;
import com.bank.repositories.UserRepository;
import com.bank.services.TransactionService;
import com.bank.services.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService
{
    
    private UserService userService;
    private PrimaryTransactionRepository primaryTransactionRepository;
    private SavingsTransactionRepository savingsTransactionRepository;
    private PrimaryAccountRepository primaryAccountRepository;
    private SavingsAccountRepository savingsAccountRepository;
    private RecipientRepository recipientRepository;
    private UserRepository userRepository;
    
    private TransactionServiceImpl(UserService userService, PrimaryTransactionRepository primaryTransactionRepository, SavingsTransactionRepository savingsTransactionRepository,
        PrimaryAccountRepository primaryAccountRepository, SavingsAccountRepository savingsAccountRepository, RecipientRepository recipientRepository, UserRepository userRepository)
    {
        this.userService = userService;
        this.primaryTransactionRepository = primaryTransactionRepository;
        this.savingsTransactionRepository = savingsTransactionRepository;
        this.primaryAccountRepository = primaryAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
        this.recipientRepository = recipientRepository;
        this.userRepository = userRepository;
    }
    
    public List<PrimaryTransaction> findPrimaryTransactions(String username)
    {
        User user = userService.findByUsername(username).get();
        return user.getPrimaryAccount().getPrimaryTransactionList();
    }
    
    public List<SavingsTransaction> findSavingsTransactions(String username)
    {
        User user = userService.findByUsername(username).get();
        return user.getSavingsAccount().getSavingsTransactionList();
    }
    
    public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction)
    {
        primaryTransactionRepository.save(primaryTransaction);
    }
    
    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction)
    {
        savingsTransactionRepository.save(savingsTransaction);
    }
    
    public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction)
    {
        primaryTransactionRepository.save(primaryTransaction);
    }
    
    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction)
    {
        savingsTransactionRepository.save(savingsTransaction);
    }
    
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception
    {
        if(transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings"))
        {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            savingsAccountRepository.save(savingsAccount);
            
            Date date = new Date();
            
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                date,
                "Transfer between accounts, from " + transferFrom + " to " + transferTo,
                "Account",
                "Finished",
                Double.parseDouble(amount),
                primaryAccount.getAccountBalance(),
                primaryAccount
            );
            primaryTransactionRepository.save(primaryTransaction);
        }
        else if(transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary"))
        {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            savingsAccountRepository.save(savingsAccount);
            
            Date date = new Date();
            
            SavingsTransaction savingsTransaction = new SavingsTransaction(
                date,
                "Transfer between accounts, from " + transferFrom + " to " + transferTo,
                "Transfer",
                "Finished",
                Double.parseDouble(amount),
                savingsAccount.getAccountBalance(),
                savingsAccount
            );
            savingsTransactionRepository.save(savingsTransaction);
        }
        else
        {
            throw new Exception("Invalid Transfer");
        }
    }
    
    public List<Recipient> findRecipients(Principal principal)
    {
        String username = principal.getName();
        return recipientRepository.findAll()
                                  .stream()
                                  .filter(recipient -> username.equals(recipient.getUser().getUsername()))
                                  .collect(Collectors.toList());
    }
    
    public Recipient saveRecipient(Recipient recipient)
    {
        return recipientRepository.save(recipient);
    }
    
    public Recipient findRecipientByName(String name)
    {
        return recipientRepository.findByName(name);
    }
    
    public void deleteRecipientByName(String recipientName)
    {
        recipientRepository.deleteByName(recipientName);
    }
    
    public void toSomeoneElse(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount)
    {
        if(accountType.equalsIgnoreCase("Primary"))
        {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                date,
                "Transfer to recipient " + recipient.getName(),
                "Transfer",
                "Finished",
                Double.parseDouble(amount),
                primaryAccount.getAccountBalance(),
                primaryAccount
            );
            primaryTransactionRepository.save(primaryTransaction);
        }
        else if(accountType.equalsIgnoreCase("Savings"))
        {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);
            
            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(
                date,
                "Transfer to recipient " + recipient.getName(),
                "Transfer",
                "Finished",
                Double.parseDouble(amount),
                savingsAccount.getAccountBalance(),
                savingsAccount
            );
            savingsTransactionRepository.save(savingsTransaction);
        }
    }
    
    @Override
    public List<User> findUsers(Principal principal)
    {
        String username = principal.getName();
        return userRepository.findAll()
                                  .stream()
                                  .filter(user -> !username.equals(user.getUsername()))
                                  .collect(Collectors.toList());
    }
    
    @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username).get();
    }
    
    @Override
    public void toOtherUser(User user, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount)
    {
        if(accountType.equalsIgnoreCase("Primary"))
        {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                date,
                "Transfer to user " + user.getUsername(),
                "Transfer",
                "Finished",
                Double.parseDouble(amount),
                primaryAccount.getAccountBalance(),
                primaryAccount
            );
            primaryTransactionRepository.save(primaryTransaction);
        }
        else if(accountType.equalsIgnoreCase("Savings"))
        {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);
        
            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(
                date,
                "Transfer to user " + user.getUsername(),
                "Transfer",
                "Finished",
                Double.parseDouble(amount),
                savingsAccount.getAccountBalance(),
                savingsAccount
            );
            savingsTransactionRepository.save(savingsTransaction);
        }
    }
    
}
