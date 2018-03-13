package com.bank.config;

import com.bank.services.impl.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.core.env.Environment;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private Environment env;
    
    private UserSecurityService userSecurityService;
    
    public SecurityConfig(Environment env, UserSecurityService userSecurityService)
    {
        this.env = env;
        this.userSecurityService = userSecurityService;
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return SecurityUtility.passwordEncoder();
    }
    
    private static final String[] PUBLIC_MATCHERS = {
        "/webjars/**",
        "/css/**",
        "/js/**",
        "/images/**",
        "/",
        "/about/**",
        "/error/**/*",
        "/contact/**",
        "/signup",
        "/console/**",
        "/forgot-password/**",
        "/reset-password/**"
    };
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS)
            .permitAll()
            .anyRequest()
            .authenticated();
        
        http.csrf()
            .disable()
            .cors()
            .disable()
            .formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
            .and()
            .rememberMe();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userSecurityService).passwordEncoder(SecurityUtility.passwordEncoder());
    }
}
