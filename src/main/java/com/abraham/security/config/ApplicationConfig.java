/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abraham.security.config;

import com.abraham.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author abrah
 */
@Configuration
@RequiredArgsConstructor 
public class ApplicationConfig {
    
    private final UserRepository repository;
    
    //Obtiene el username del contexto de Seguridad de Spring, donde ya hemos obtenido de
    // la petición, el username y la contraseña.
    @Bean
    public UserDetailsService userDetailsService(){
        //Obtiene el email de la base de datos mediante consula jpa gracias a repository.
        return username -> repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
    
    //Encargado de valida las credenciales basadas en una base de datos 
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
       //Se encargará de comprobar la contraseña obtenida en el token, con la cifrada en la base de datos.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    //Encargado de delegar a los AuthenticationProvider configurados, el proceso de validación
    // de credenciales.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    
    //Configuramos el codificador de contraseñas con tal de qué verifique.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
