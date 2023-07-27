# console
Projecto Spring Boot 3 con todo lo requerido

### Iniciar ejecutando el siguiente comando:
````
cd /console
docker-compose -f compose-prod.yaml up  --build
````

### Detener ejecutando el siguiente comando:
````
cd /console
docker-compose -f compose-prod.yaml down
````

### Lanzar un test en particular:
````
mvn test -Dtest="CreateEventDtoTest"
mvn test -Dtest="EventControllerTest"
````

### Lanzar los test:
````
mvn test
````
