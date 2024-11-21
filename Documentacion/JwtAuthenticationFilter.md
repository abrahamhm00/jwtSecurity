[[Spring Framework]] [[OncePerRequestFilter]]

Comprueba la entrada de una Request, para darle acceso a el Registro de usuario o Inicio de Sesion, según los filtros configurados en esta clase.

```
package com.abraham.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author abrah
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, 
            @NonNull HttpServletResponse response, 
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        //Si no hay un token en el Header, pasa el filtro y pasamos al siguiente filtro de la cadena.
        if(authHeader==null || !authHeader.startsWith(" Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        // El token está posicionado a partir del 7 caracater del String qué compone el Header de la Request.
        jwt = authHeader.substring(7);
        userEmail =  jwtService.extractUsername(jwt);//Extraemos el userEmail desade el JWt token, siendonesto un servicio que nos presta jwt;
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null ){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken  = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        }
    }
    
}
```

- **doFilterInternal(@NonNull HttpServletRequest request, 
            @NonNull HttpServletResponse response, 
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException):** Aquí se procesan todas las solicitudes entrantes.
- Extraemos del Header un posible token,si no es asi, quiere decir qué el usuario aún no se ha registrado, le dejamos pasar **filterChain.doFilter(request, response);
            return;** para qué se resgistre, solo le damos acceso a eso, ya que no actualizamos el SecurityContextHolder.
    Si el Header posee un token, y es de tipo JWT, procedemos a extraer el email gracias a [[JwtService]] .extractUsernsame(jwt); para después comprobar si el email no está vacío, y el usuario no estña autenticado en el SecurityContextHolder.
    Si es un token válido, creamos un UsernamePasswordAuthenticationToken, para poder autorizar al usuario en el SecurityContextHolder.
    Dejamos que se ejecute el siguiente filtro en la cadena.
    
    