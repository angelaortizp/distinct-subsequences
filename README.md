# Distinct Subsequences Service 

Este proyecto es un servicio para calcular y gestionar subsecuencias distintas entre dos cadenas de texto utilizando algoritmos de programación dinámica. El proyecto está basado en Spring Boot y sigue una arquitectura modular y escalable.

## Funcionalidades 

### 1. Cálculo de Subsecuencias Distintas 

Este servicio ofrece la capacidad de calcular el número de subsecuencias distintas entre dos cadenas de texto, siguiendo las restricciones de longitud y caracteres permitidos.

### 2. Gestión de Subsecuencias 

Además del cálculo, el servicio permite:

- Guardar subsecuencias calculadas.

- Recuperar subsecuencias por su ID.

- Listar todas las subsecuencias guardadas.

- Actualizar subsecuencias existentes.

- Eliminar subsecuencias por ID.

## Stack Tecnológico 

- Java 17

- Spring Boot 3.1.2

- Maven

- JPA / Hibernate

- APIs RESTful

- MySQL

## Primeros Pasos 

### Preparación de la Base de Datos

1. Localiza los siguientes archivos SQL en el directorio raíz del proyecto y ejecutalos:
   - `ScriptCreateInsertSubsequences`

### Configuración del Proyecto 
 
1. Clona el repositorio:


```bash
git clone https://github.com/usuario/distinct-subsequences.git
```
 
2. Navega al directorio del proyecto:


```bash
cd distinct-subsequences
```
 
3. Construye el proyecto:


```Copy code
mvn clean install
```
 
4. Configura la base de datos en `src/main/resources/application.properties` con tus credenciales de MySQL.
 
5. Ejecuta el servicio:


```arduino
mvn spring-boot:run
```

## Documentación de la API 

La documentación de la API se genera automáticamente mediante Swagger y está disponible en:


```bash
http://localhost:8089/swagger-ui.html
```

## Pruebas 

El proyecto incluye un conjunto de pruebas unitarias y de integración que pueden ejecutarse con:


```bash
mvn test
```

## Importación de Colecciones de Postman 

Para probar las APIs, puedes importar la colección de Postman que se encuentra en el directorio raíz del proyecto.
 
1. Abre Postman.
 
2. Haz clic en "Import" y selecciona el archivo `DistinctSubsequencesAPI.postman_collection.json`.
