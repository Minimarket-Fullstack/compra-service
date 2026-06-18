# compra-service
Microservicio encargado de registrar compras a proveedores. Valida proveedores y productos mediante comunicación con otros microservicios y calcula el total de la compra.
## Puerto
```
8088
```
## Tecnologías
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Eureka Client
- Swagger/OpenAPI
- HATEOAS
- Mockito/JUnit
- Docker
- Railway

## Base de datos
```
db_minimarket
```
## Endpoints V1
```
GET /api/v1/compras
GET /api/v1/compras/{id}
GET /api/v1/compras/proveedor/{proveedorId}
POST /api/v1/compras
PATCH /api/v1/compras/{id}/estado
DELETE /api/v1/compras/{id}
```
## Endpoints V2 HATEOAS
```
GET /api/v2/compras
GET /api/v2/compras/{id}
GET /api/v2/compras/proveedor/{proveedorId}
POST /api/v2/compras
PATCH /api/v2/compras/{id}/estado
DELETE /api/v2/compras/{id}
```
## Swagger
```
http://localhost:8088/doc/swagger-ui.html
```
## Ejemplo JSON
```json
{
  "proveedorId": 1,
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 10
    }
  ]
}
```
## Ejecutar pruebas
```bash
mvn test
```
## Ejecutar localmente
```bash
mvn spring-boot:run
```
## Configuración Railway
```properties
server.port=${PORT:8088}
```
Variables recomendadas:

```properties
SPRING_DATASOURCE_URL=jdbc:mysql://HOST:PORT/railway?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
SPRING_DATASOURCE_USERNAME=TU_USUARIO
SPRING_DATASOURCE_PASSWORD=TU_PASSWORD
EUREKA_CLIENT_ENABLED=false
```
