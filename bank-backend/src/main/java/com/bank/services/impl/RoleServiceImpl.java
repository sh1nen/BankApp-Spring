package com.bank.services.impl;

import com.bank.domain.security.Role;
import com.bank.repositories.RoleRepository;
import com.bank.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService
{
    private RoleRepository roleRepository;
    
    public RoleServiceImpl(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }
    
    @Override
    public Role findByName(String name)
    {
        return roleRepository.findByName(name).get();
    }
}
