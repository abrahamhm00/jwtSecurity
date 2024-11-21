[[Spring Framework]]

### RequestMapping
	- Mapea solicitudes HTTP a  clases o metodos esecificos
	- Puede usarse tanto a nvel de clase como a nivel de metodo
	- Soporta varios metodos HTTP

### (Get,Post,Put,Delete,Patch)Mapping
	- Son especializaciones de @RequestMapping para manejar solicitudes HHTP GET,POST,PUT,etc

### RequestParam
	- Mapea los parámetros de una solicitud HTTP(query params) a parámetros del nmétodo.
	- Es útil cuando los datos son enviados como parámetros de URL.(ej: `/search?name=John`).

### PathVariable 
	- Mapea variables de la URL a los parámetros del método
	- Se utiliza para capturar valores dentro de la URL, útil cuando un recurso está identificado por un valor en la ruta (ej: `/users/{id}`).
### RequestBody
	- Indica que el cuerpo de la solicitud HTTP debe vincularse a un parámetro de método.
### ResponseBody
	- Indica qué el valor devuelto por el método debe ser usado como el cuerpo de la respuesta HTTP.

### RestController
	- Combina @Controller y @ResponseBody, indicando qué el controlador maneja solicitudes REST y sus respuestas serán el cuerpo de la respuesta HTTP.
### ModelAttribute
	-Vincula los datos de un formulario o parámetros de solicitud a un modelo.
	- Se usa para preparar objetos en el modelo para las vistas, como un objeto de formulario qué será utilizado en una página JSP, por ejemplo.
### RequestHeader
	- Extrae los encabezados HTTP de la solicitud y los vincula a parámetros del método.
	- Permite acceder a informacion relevante que se envía a través de los encabezados, como autenticación, detalles del navegador, etc.

### CookieValue
	- Mapea los valores de las cookies a los parámetros del método.

### SessionAttribute
	- Obtiene los atributos de la sesion HTTP
	- Para acceder fácilmente a datos de la sesión HTTP sin necesidad de acceder manualmente al objeto `HttpSession`.
### RequestPart
	- Vincula una parte de una solicitud multipart/form-data a un parámetro del método.
	- Facilita el manejo de cargas de archivos u otros datos binarios en solicitudes multipart.
### CrossOrigin
	- Configura el CORS en controladores o métodos
	- Es crucial para permitir que aplicaciones web de diferentes dominios puedan comunicarse con tu API (evitar bloqueos por CORS).
### ExceptionHandler
	- Maneja las excepciones en métodos de controladores específicos.
### ControllerAdvice
	- Define un componente global para manejar excepciones en múltiples controladores
