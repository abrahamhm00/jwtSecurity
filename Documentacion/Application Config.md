[[Spring Framework]]

````
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
    
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

````


- **UserDetailsService**: Función Lambda qué retorna el usuario del repositorio. el cuál el cliente ha enviado con la solicitud. Si finbyEmail lo encuentra, se devuelve un objeto UserDetails.
- **AuthenticationProvider:** Nos provee de un tipo de autenticador, en este caso(Dao), qué devuelve un objeto autenticate conforme se autentica al usuario mediante una consulta a la base de datos(UserDetailsService, con los datos proporcionados por AuthenticationManager), y la comparación de la contraseña encriptada en la base de datos, con la proporcionada por el authManager(Encriptada con el PasswordEncoder),para saber si existe.
- **AuthenticationManager:** Recibe un objeto authentication, que contiene el usuario y la contraseña proporcionadas por la solicitud del cliente, y este delega al authentication provider pertinente, para que este verifique la autenticidad del usuario.
- **PassswordEnconder:** Se encarga de hashear las contraseñas para almacenarlas en la base de datos, y para corroborar la proporcionada por un cliente, con la de un usuario, para autenticarlo. En este caso lo configuramos con Bcrypt.