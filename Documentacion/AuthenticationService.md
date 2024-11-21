[[Spring Framework]]

Se encargará de aplicar la logica pertinente al endpoint al cual va asociado. Si por ejemplo tenemos un endpoint qué recoge la solicitud HTTP con los datos del cliente que se quiere registrar, aqui utilizamos esos datos para registrar al usuario en la base de datos, y en nuestro caso, también generarle un token el cuál será devuelto en la respuesta HTTP.

```
package com.abraham.security.auth;

import com.abraham.security.user.Role;
import com.abraham.security.user.User;
import com.abraham.security.user.UserRepository;
import com.abraham.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author abraham
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                .firstname(request.getNombre())
                .lastname(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate
            (new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
```

-  **AuthenticationResponse  register:** Metodo de tipo AuthenticationResponse, ya qué devolveremos una clase la cual almacena el token generado para el usuario.
	- Creamos un usuario gracias al metodo Builder() proporcionado por Lombok y anotado en la clase User, con los datos obtenidos del endpoint.
	- Creamos un token jwtService.generateToken(user) y luego creamos una instancia de la clase AuthenticationResponse, la cual almacenará este, y lo devolverá a la respuesta del endpoint.
- **AuthenticationResponse authenticate:**  Metodo de tipo AuhthenticationResponse, ya qué devolveremos una clase la cual almacena el token generado por el usuario.
	- authenticationManager.authenticate: le pasamos por parámetro un objeto de tipo autehnticate, con las credenciales de inicio de sesion qué nos proporciona el cliente con la peticion. Este se encarga de ejecutar el authentication Provider qué hemos configurado en [[Application Config]] donde comprobará mediante un [[UserDetails]] el cual hemos configurado tambien, si las credenciales proporcionadas coinciden con las referidas en el usuario almacenado en la base de datos.
	- Crea el usuario, buscandolo en el repositorio mediante el email con consultas jpa gracias a la clase [[JpaRepository]] , genera un token con el usuario, y construye el AuthenticationResponse con este.