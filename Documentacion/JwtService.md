[[Spring Framework]] 
### 3. Configuración de Seguridad con JWT

##### a) Utilidad para JWT (`JwtUtil`)
```
package com.abraham.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author abrah
 */
@Service
class JwtService {

    private static final String SECRET_KEY = "Np253gGX5PSl5deSPqc5DkvZ9lfj79FG";
    
    String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    
    public String generateToken(
            Map <String,Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000 * 60 *24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
        
    }
```
###### Explicación:

- **SECRET_KEY**: La clave secreta utilizada para firmar el JWT. Es importante mantenerla segura, ya que si alguien tiene acceso a ella, podría generar tokens válidos y comprometer la seguridad.
    
- **extractUsername(String token)**: Extrae el nombre de usuario (o sujeto) del token JWT, lo que permite identificar quién es el usuario.
    
- **extractExpiration(String token)**: Extrae la fecha de expiración del token, lo que se usa para validar si el token sigue siendo válido.
    
- **extractClaims(String token, Function<Claims, T> claimsResolver)**: Permite extraer cualquier reclamo específico del token. Los reclamos (claims) son la información que contiene el token, como el usuario, la expiración, etc.
    
- **extractAllClaims(String token)**: Extrae todos los reclamos del token. Internamente usa la librería `io.jsonwebtoken` para interpretar el token y obtener su contenido.
    
- **isTokenExpired(String token)**: Verifica si el token ha expirado comparando la fecha de expiración con la fecha actual.
    
- **generateToken(UserDetails userDetails)**: Genera un JWT para un usuario basado en su nombre de usuario. Incluye información básica (como el nombre de usuario) y una fecha de expiración. El token se firma con `HS256` (un algoritmo HMAC de 256 bits).
    
- **generateToken(Map<String, Object> claims, String subject)**: Construye el token real con los reclamos (claims) y el sujeto (generalmente, el nombre de usuario). También agrega la fecha de emisión y de expiración.
- **getSignInKey():** Este método `getSignInKey()` se utiliza para obtener una clave secreta que permite firmar tokens JWT (JSON Web Tokens) usando el algoritmo HMAC SHA. Desglosemos el código:

1. **`SECRET_KEY`**: Es una constante de tipo `String` que contiene la clave secreta codificada en Base64.
2. **`Decoders.BASE64.decode(SECRET_KEY)`**: Esta línea decodifica el `SECRET_KEY` en Base64, lo que convierte la clave en un arreglo de bytes (`byte[] keyBytes`).
3. **`Keys.hmacShaKeyFor(keyBytes)`**: Este método crea una instancia de `Key` utilizando el arreglo de bytes, configurada para el algoritmo HMAC SHA. La clase `Keys` de la biblioteca `jjwt` facilita la creación de claves seguras para la firma de tokens.
4. 
- **validateToken(String token, UserDetails userDetails)**: Valida el token asegurando que:
    
    - El nombre de usuario en el token coincida con el `UserDetails` proporcionado.
    - El token no esté expirado.

> **Por qué se implementa:** Esta clase se encarga de toda la lógica relacionada con JWT, como generar, extraer información, y validar tokens. JWT es fundamental en aplicaciones sin estado (stateless), donde el servidor no guarda información de sesiones; todo se almacena en el token.
