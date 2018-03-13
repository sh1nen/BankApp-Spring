package com.bank.services.impl;

import com.bank.repositories.UserRepository;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserSecurityService implements UserDetailsService
{
    
    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);
    
    private UserRepository userRepository;
    
    @Autowired
    public UserSecurityService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByUsername(username).map(user -> {
                                LOG.warn("Username {} found", user);
                                return user;
                            }).orElseThrow(() -> new UsernameNotFoundException("User {} not found"));
        
    }
}
