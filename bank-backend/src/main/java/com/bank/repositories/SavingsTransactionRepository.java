package com.bank.repositories;

import com.bank.domain.SavingsTransaction;
import org.springframework.data.repository.CrudRepository;

public interface SavingsTransactionRepository extends CrudRepository<SavingsTransaction, Long>
{
}
