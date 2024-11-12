/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abraham.security.config;

import com.abraham.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author abrah
 */
@Configuration
@RequiredArgsConstructor 
public class ApplicationConfig {
    
    private final UserRepository repository;
    
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
