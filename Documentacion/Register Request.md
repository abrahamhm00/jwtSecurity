[[Spring Framework]] 
Clase con sus respectivas anotaciones Lombok, qué nos  dice las credenciales qué el cliente nos debe enviar con tal de registrar  a un usuario.

```
package com.abraham.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author abraham
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
```

