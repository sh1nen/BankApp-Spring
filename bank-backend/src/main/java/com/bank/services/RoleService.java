package com.bank.services;

import com.bank.domain.security.Role;

public interface RoleService
{
    Role findByName(String name);
}
