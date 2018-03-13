package com.bank.services;

import com.bank.domain.User;
import com.bank.domain.security.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService
{
    void save(User user);
    
    User createUser(User user, Set<UserRole> roles);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    void updatePassword(String password, Long userId);
    
    List<User> findUserList();
    
    void enableUser(String username);
    
    void disableUser(String username);
}
