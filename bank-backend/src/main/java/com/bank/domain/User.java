package com.bank.domain;

import com.bank.domain.security.Authority;
import com.bank.domain.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class User implements UserDetails, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    
    public User(String username, String password, String firstName, String lastName, String email, String phone)
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String phone;
    
    private boolean enabled = true;
    
    @OneToOne
    private PrimaryAccount primaryAccount;
    
    @OneToOne
    private SavingsAccount savingsAccount;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointmentList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipient> recipientList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
        return authorities;
    }
    
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }
    
    @Override
    public boolean isEnabled()
    {
        return enabled;
    }
    
}
