# mc-login
Login Java EE (Servlets/JSP) con Maven y MySQL.

## Requisitos
- JDK 11
- Maven 3.x
- Tomcat 9
- MySQL 8

## Configuración
1) Copia `src/main/resources/db.properties.example` a `src/main/resources/db.properties`.
2) Edita usuario/clave/URL.
3) `mvn clean package` y despliega `target/mc-login.war` en Tomcat.
