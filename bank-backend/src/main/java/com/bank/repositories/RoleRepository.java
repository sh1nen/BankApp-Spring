package com.bank.repositories;

import com.bank.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long>
{
    Optional<Role> findByName(String name);
}
