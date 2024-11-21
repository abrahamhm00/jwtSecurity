[[Spring Framework]] [[UserDetails]]

## **Microservicio de autenticación de usuarios (User Service)**


Desarrollo de un **servicio de autenticación completamente funcional**. Los usuarios deben poder registrarse, iniciar sesión, y recibir un token JWT. Las rutas protegidas solo serán accesibles con un JWT válido.
- **Tareas**:
    1. Implementar registro de usuarios con JWT para autenticación.
    2. Crear endpoints para registro e inicio de sesión.
    3. Validar datos de entrada y salida con DTOs (Data Transfer Objects).
    4. Añadir pruebas unitarias básicas para el registro e inicio de sesión.
### 1.Modelo de Usuario

```
package com.abraham.security.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author abrah
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword(){
        return password;
    }
    
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```
##### Explicación:

- **@Data:** Lombook anotation qué genera los getter y los setter.
- **Builder:** Lombook anotation permite crear objetos segun un patrón de diseños Builder.
- **NoArgsConstructor:** Genera un constructor sin argumentos, útil para instanciar clases sin necesidad de empleae parámetros.
- **@AllArgsConstructor:** Genera un constructor con un parametro para cada campo en la clase.
- **@Entity**: Marca esta clase como una entidad de JPA, lo que indica que esta clase se mapeará a una tabla en la base de datos.
- **@Table(name = "users")**: Define que esta entidad se guardará en la tabla `users`.
- **@Id y @GeneratedValue**: La propiedad `id` será la clave primaria de la tabla, y el valor de esta clave se generará automáticamente mediante una estrategia (en este caso `IDENTITY`, que permite que la base de datos maneje la generación del valor).
- **@Column**: Los atributos `username`, `email` y `password` se mapearán a columnas en la tabla `users`. Se usan restricciones como `nullable = false` (no se permite `null`) y `unique = true` (valores únicos) para asegurar que el nombre de usuario y el email no se repitan.
- **role**: Es un campo para definir el rol del usuario. Aquí se establece el valor por defecto como `"USER"`. Esto puede usarse para diferenciar niveles de acceso (e.g., `ADMIN` vs `USER`).
