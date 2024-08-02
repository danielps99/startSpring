# startSpring
Project for study using the Spring Framework without dependency initially. I intend to add dependency as needed.

## Document topics
- [Run with Docker Compose](#run-with-docker-compose)
  - [Requirements to compile and run inside docker](#requirements-to-compile-and-run-inside-docker)
  - [Requirements to compile locally and run inside docker](#requirements-to-compile-locally-and-run-inside-docker)
  - [Compile and run inside docker](#compile-and-run-inside-docker)
  - [Compile locally and run inside docker](#compile-locally-and-run-inside-docker)
- [Run with local Java and no docker](#run-with-local-java-and-no-docker)
  - [Requirements to compile and run locally](#requirements-to-compile-and-run-locally)
  - [Compile and run locally](#compile-and-run-locally)
- [How to use the application](#how-to-use-the-application)
- [How to access database](#how-to-access-database)

## Project progress
- Historic of first main commits
  - See on [link](./README_historyCommits.md) the oldest commits.
- Newest commits
  - For recent commits, I have been using semantic commit.

## Run with Docker Compose
### The docker compose configuration includes.
- java 17 container
- Mysql 8 container

You can compile and run inside docker container, or compile locally and run inside docker container.
### Requirements to compile and run inside docker
- Docker Engine: https://docs.docker.com/engine/install/
- Docker Compose https://docs.docker.com/compose/install/

### Requirements to compile locally and run inside docker
- The same of requirements to compile and run inside docker, mentioned above.
- Java 17
- Maven 3.5 or newer version


### Compile and run inside docker
The command bellow run application with tests. 
```
    docker-compose up --build --force-recreate start-spring-api
```
The command bellow run application with no tests.
```
    DOCKERFILE=./Dockerfile.skipTests docker-compose up --build --force-recreate start-spring-api
```
### Compile locally and run inside docker
The first command bellow compile the project with tests. The second command run application.
```
    mvn clean install
    DOCKERFILE=./Dockerfile.requireLocalMaven docker-compose up --build --force-recreate start-spring-api
```

## Run with local Java and no docker
In this case H2 Database will be the database.
### Requirements to compile and run locally
- Java 17
- Maven 3.5 or newer version
### Compile and run locally
Run the application.
```
mvn spring-boot:run
``` 
Run only the unit tests of the project.
```
mvn spring-boot:run test
``` 

## How to use the application
The project contains only backend, so you need any tools like postman or similar to request the system.

If you use Postman, inside the project there is the file StartSpringAPI.postman_collection.json, import it in Postman, it is helpfully.

### How to log in the application
Authenticate user in

http://localhost:8080/login

body content to log in.
- User admin: {"username":"admin", "password":"admin"}
- User editor: {"username":"editor", "password":"editor"}

After log in, copy the Authorization key inside header and past it inside header Authorization to use on next requests. 

### Swagger
Swagger documents api and can request api. But there is no login request in Swagger, so you need to authenticate in other tool, like postman for example, in order to get token.

Swagger url: http://localhost:8080/swagger-ui/index.html

## How to access database
### Connection details when run with local Java
In this case the project use H2 Database Engine

**Url :** http://localhost:8080/h2-console

**JDBC Url :** jdbc:h2:mem:banco

**User Name :** developer

**Password :** freeaccess

### Connection details when run with Docker
In this case the project use Mysql database inside Docker container

**Hostname :** localhost

**Port :** 3306

**User Name :** developer

**Password :** freeaccess