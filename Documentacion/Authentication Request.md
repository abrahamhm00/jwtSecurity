[[Spring Framework]] 

Clase con sus respectivas anotaciones Lombok, qué nos dice las credenciales qué el cliente nos debe enviar con tal de loggear un usuario de la base de datos.

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
public class AuthenticationRequest {
    private String email;
    private String password;
}
```

