package com.bank.repositories;

import com.bank.domain.SavingsAccount;
import org.springframework.data.repository.CrudRepository;

public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Long>
{
    SavingsAccount findByAccountNumber(int accountNumber);
    
}
