package com.ctrl.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(requests -> requests
                        .antMatchers("/api/auth/**").permitAll()
                        .antMatchers("/api/products/**").permitAll() // Permit all for products
                        .antMatchers("/api/reviews/**").permitAll() // Permit all for reviews
                        // permit all for address and payments
                        .antMatchers("/api/addresses/**").permitAll()
                        .antMatchers("/api/payment-methods/**").permitAll()
                        .antMatchers("/api/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/manager/**").hasRole("COMMUNITY_MANAGER")
                        .antMatchers("/api/merchant/**").hasRole("MERCHANT")
                        .anyRequest().authenticated())
                .formLogin(login -> login.permitAll());
    }
}
