package com.bank.services;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.PrimaryTransaction;
import com.bank.domain.Recipient;
import com.bank.domain.SavingsAccount;
import com.bank.domain.SavingsTransaction;
import com.bank.domain.User;

import java.security.Principal;
import java.util.List;

public interface TransactionService
{
    
    List<PrimaryTransaction> findPrimaryTransactions(String username);
    
    List<SavingsTransaction> findSavingsTransactions(String username);
    
    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);
    
    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
    
    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);
    
    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
    
    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception;
    
    List<Recipient> findRecipients(Principal principal);
    
    Recipient saveRecipient(Recipient recipient);
    
    Recipient findRecipientByName(String name);
    
    void deleteRecipientByName(String recipientName);
    
    void toSomeoneElse(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);
    
    List<User> findUsers(Principal principal);
    
    User findByUsername(String username);
    
    void toOtherUser(User user, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);
    
}
