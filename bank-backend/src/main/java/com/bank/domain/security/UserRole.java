package com.bank.domain.security;

import com.bank.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;
    
    public UserRole(User user, Role role)
    {
        this.user = user;
        this.role = role;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    
}
