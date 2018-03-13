package com.bank.repositories;

import com.bank.domain.Recipient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipientRepository extends CrudRepository<Recipient, Long>
{
    List<Recipient> findAll();
    
    Recipient findByName(String recipientName);
    
    void deleteByName(String recipientName);
}
