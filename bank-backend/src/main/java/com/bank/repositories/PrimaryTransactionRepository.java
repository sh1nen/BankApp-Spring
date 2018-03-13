package com.bank.repositories;

import com.bank.domain.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;

public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, Long>
{
}
