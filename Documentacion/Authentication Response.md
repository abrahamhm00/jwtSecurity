[[Spring Framework]]

En [[Authentication Controller]] debemos devolver un ResponseEntity de tipo Authentication Response, pues utilizamos esta clase, para definir la respuesta a una solicitud HTTP, en nuestro caso, el token del usuario registrado o loggeado, con tal de saber si ha sido autenticado.

```
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
```

- Al utilizar la anotación @AllArgsConstructor de Lombook, este crea un constructor con  parámetros, con tal de inicializar las variables de la clase, y asi pues, aquí proporcionaremos el token creado si finalmente el usuario está autenticado.
