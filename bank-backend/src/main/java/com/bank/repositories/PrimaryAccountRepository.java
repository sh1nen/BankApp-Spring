package com.bank.repositories;

import com.bank.domain.PrimaryAccount;
import org.springframework.data.repository.CrudRepository;

public interface PrimaryAccountRepository extends CrudRepository<PrimaryAccount, Long>
{
    PrimaryAccount findByAccountNumber(int accountNumber);
}
