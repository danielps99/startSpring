# startSpring
Project for study using the Spring Framework without dependency initially. I intend to add dependency as needed. 

## Project progress
- Historic of first main commits
  - See on [link](./README_historyCommits.md) the oldest commits.
- Newest commits
  - For recent commits, I have been using semantic commit.

## General Requirements
Install Maven and see Maven version.
```
sudo apt install maven
mvn --version
```

## How to run with Docker Compose
Install docker compose, add user to docker group and activate the changes to groups.
```
sudo apt install docker-compose
sudo usermod -aG docker $USER
newgrp docker
```

### Steps to run the project.
The docker compose configuration includes.
- java 11 container
- Mysql 8 container

Inside the project root folder, run command below to download and install dependencies.
```
mvn install
``` 
Inside the project root folder, run command below to create, start docker containers and run the project.
```
    docker-compose up --build --force-recreate
```

## How to run with local Java
In order manage the Java version use the sdkman https://sdkman.io. Install the Sdkman and the Java 11.
```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version
sdk install java 11.0.9-zulu
``` 
### Steps to run the project.
Inside the project root folder, run command below to download and install dependencies.
```
mvn install
``` 
Inside the project root folder, run command below to run the project.
```
mvn spring-boot:run
``` 

## How to run only unit tests
Inside the project root folder, run command below to run the unit tests of the project.
```
mvn spring-boot:run test
``` 

## How to use the system
The project contains only backend, so you need any tools like postman or similar to request the system.
### How to log in the system
Autenticate user in

http://localhost:8080/login

body content to log in.
- User admin: {"username":"admin", "password":"admin"}
- User editor: {"username":"editor", "password":"editor"}

After log in, copy the Authorization key inside header and past it inside header Authorization to use on next requests. 

### Request to create a product
To create a product you must be logged as admin. Send post to:

http://localhost:8080/api/product

Object example:
```
{
    "sku": "123456789",
    "description": "First product",
    "measurementUnit": "KG",
    "unitPrice": 15.12
}
``` 

## How to access database
### Connection details when run with local Java
In this case the project use H2 Database Engine

**Url :** http://localhost:8080/h2-console

**JDBC Url :** jdbc:h2:mem:banco

**User Name :** developer

**Password :** freeaccess

### Connection details when run with Docker Compose
In this case the project use Mysql database inside Docker container

**Hostname :** localhost

**Port :** 3306

**User Name :** developer

**Password :** freeaccess