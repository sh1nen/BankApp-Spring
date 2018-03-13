package com.bank.repositories;

import com.bank.domain.password.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>
{
    PasswordResetToken findByToken(String token);
}
