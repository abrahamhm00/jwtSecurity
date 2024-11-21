+-------------------------+
|       User Repository        | <--- Persistencia (JpaRepository)
|                               |       - Gestiona los usuarios en la base de datos.
|                               |       - Extiende JpaRepository.
+-------------------------+
           |
           |
           v
+-------------------------+
|    Application Config        | <--- Configuración (Spring)
|                               |       - Define beans de la aplicación.
|                               |       - Configura dependencias como servicios.
+-------------------------+
           |
           |
           v
+-------------------------+
|   AuthenticationService      | <--- Lógica de Negocio
|                               |       - Autenticación y registro de usuarios.
|                               |       - Enlace con UserDetails para la validación.
+-------------------------+
           |
           |
           v
+-------------------------+
|      User Details            | <--- Gestión de Usuarios
|                               |       - Implementa `UserDetails`.
|                               |       - Proporciona datos de usuario a Spring Security.
+-------------------------+
           |
           |
           v
+-------------------------+
|          User                | <--- Entidad (Modelo de datos)
|                               |       - Representa un usuario en el sistema.
|                               |       - Incluye propiedades como username y password.
+-------------------------+
           |
           |
           v
+-------------------------+
|       JwtService             | <--- Seguridad (Tokens)
|                               |       - Genera y valida tokens JWT.
|                               |       - Extrae datos como roles y usernames.
+-------------------------+
           |
           |
           v
+-------------------------+
|    Authentication Request    | <--- DTO (Solicitud)
|                               |       - Representa las credenciales del usuario.
|                               |       - Usado en la autenticación.
+-------------------------+
           |
           |
           v
+-------------------------+
|   Authentication Response    | <--- DTO (Respuesta)
|                               |       - Retorna el JWT al cliente.
+-------------------------+
           |
           |
           v
+-------------------------+
|   Authentication Controller  | <--- Capa de Presentación
|                               |       - Maneja endpoints `/register` y `/login`.
|                               |       - Llama a `AuthenticationService`.
+-------------------------+
           |
           |
           v
+-------------------------+
|     Security Config           | <--- Configuración de Seguridad
|                               |       - Define rutas protegidas y abiertas.
|                               |       - Configura filtros de seguridad.
|                               |       - Registra JwtAuthenticationFilter.
+-------------------------+
           |
           |
           v
+-------------------------+
|   JwtAuthenticationFilter     | <--- Filtros de Seguridad
|                               |       - Extiende `OncePerRequestFilter`.
|                               |       - Valida el token en cada solicitud.
+-------------------------+
           |
           |
           v
+-------------------------+
|     OncePerRequestFilter      | <--- Clase Base (Spring Security)
|                               |       - Asegura que cada solicitud sea procesada una vez.
+-------------------------+

