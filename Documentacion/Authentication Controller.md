[[Spring Framework]]

Se encargará de Mappear las rutas a los endpoints, los cuales aplicarán la logica pertinente de la clase Service.
 ```
 package com.abraham.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author abraham
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService service;
    
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
    @RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
    @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
```

- **Anotaciones del Controlador:**
		-  RestController: Configura la clase como un controlador qué va a manejar las solicitudes REST. 
		-  RequestMapping: Mapea las request con la ruta del parámetro, a este controlador.
		-  RequiredArgsConstructor: El constructor de esta clase requerirá argumentos para instanciara un objeto de este.
-  **ResponseEntity:** Clase de Spring Framework qué permite modificar la respuesta HTTP qué se le enviará al cliente, a parte del objeto qué le será retornado en el cuerpo de la cabecera.
		- **`ResponseEntity.ok()`**: Este es un atajo para devolver una respuesta con el código de estado `200 OK`. El objeto `obj` se convierte automáticamente en el cuerpo de la respuesta.