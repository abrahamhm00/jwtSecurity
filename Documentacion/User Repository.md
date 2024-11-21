
[[Spring Framework]] [[JpaRepository]]
### 2. Repositorio de Usuario

```
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
```
##### Explicación:

- **JpaRepository<User, Long>**: Esta interfaz extiende de `JpaRepository`, que es parte de Spring Data JPA. Proporciona métodos básicos para operaciones CRUD (Create, Read, Update, Delete) sin necesidad de implementarlos manualmente.
- **Métodos `findByUsername` y `findByEmail`**: Estos son métodos personalizados que te permiten buscar un usuario por su nombre de usuario (`username`) o por su correo electrónico (`email`). Devuelven un `Optional<User>` que es un contenedor que puede o no contener un valor. Esto ayuda a evitar `NullPointerException`.