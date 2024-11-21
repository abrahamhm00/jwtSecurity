[[Spring Framework]]

Configuracion de como se van a comportar las funciones de seguridad, los filtros,etc, qué hemos configurado previamente.

```
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author abrah
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class); 
        return http.build();
    }
            
}
```

- Creamos un metido de tipo SecurityFilterChain, qué nos devolverá un httpSecurity object, con las configuraciones siguientes:
		-  **csrf().disable():** Deshabilita la proteccion contra CSRF, ya qué usamos JWT token para autenticar cada petición, así pues no pueden realizar ningún ataque de este tipo.
		- **authorizeHttpRequests()
            .requestMatchers("")
            .permitAll()
            .anyRequest()
            .authenticated():** Autoriza las peticiones qué cumplan los Matchers de url, permitiendoles el acceso, y anyRequest tendrá qué estar autenticada para ser autorizada.
        - **sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS):** Configura el sistema para qué no mantenga las sesiones entre las solicitudes, ya qué las autenticaremos cada vez que se envien, grácias al JWT token.
		- **.authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class); :** 
            Establece un proveedor de autenticacion personalizado, y después añadimos el filtro de jwtAuthFilter, antes de la auatenticación de usuarios, para procesar la solicitud y validar el JWT token.