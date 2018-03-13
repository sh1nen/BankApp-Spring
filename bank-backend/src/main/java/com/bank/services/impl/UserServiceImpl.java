package com.bank.services.impl;

import com.bank.domain.User;
import com.bank.domain.security.UserRole;
import com.bank.repositories.RoleRepository;
import com.bank.repositories.UserRepository;
import com.bank.services.AccountService;
import com.bank.services.UserService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService
{
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AccountService accountService;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Autowired
    private void setAccountService(AccountService accountService)
    {
        this.accountService = accountService;
    }
    
    @Override
    public void save(User user)
    {
        userRepository.save(user);
    }
    
    @Override
    public User createUser(User user, Set<UserRole> roles)
    {
        Optional<User> localUser = userRepository.findByUsername(user.getUsername());
        if(localUser.isPresent())
        {
            LOG.info("User with username {} already exists. Nothing will be done", user.getUsername());
            return localUser.get();
        }
        else
        {
            String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            roles.forEach(userRole -> roleRepository.save(userRole.getRole()));
            user.getUserRoles().addAll(roles);
            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());
            return userRepository.save(user);
        }
    }
    
    @Override
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(password, userId);
    }
    
    public List<User> findUserList()
    {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }
    
    @Transactional
    public void enableUser(String username)
    {
        User user = findByUsername(username).get();
        if(user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userRepository.save(user);
    }
    
    @Transactional
    public void disableUser(String username)
    {
        User user = findByUsername(username).get();
        user.setEnabled(false);
        userRepository.save(user);
    }
}
