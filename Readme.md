# Spring Boot, MySQL, JPA, Hibernate Rest API Tutorial

Build Restful CRUD API for a simple Note-Taking application using Spring Boot, Mysql, JPA and Hibernate.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 5.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/pradeepIgreendata/SerenityBDDWithGatling
```

**2. Create Mysql database**
```bash
create database notes_app
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/easy-notes-1.0.0.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
or for test purpose
mvn spring-boot:start
and after test execution
mvn spring-boot:stop
```

**5. Build and run the Serenity BDD tests using maven**

```bash
mvn clean verify
```

**6. Build and run the Gatling Performance tests using maven**

```bash
mvn spring-boot:start gatling:test spring-boot:stop
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

    GET /api/notes
    
    POST /api/notes
    
    GET /api/notes/{noteId}
    
    PUT /api/notes/{noteId}
    
    DELETE /api/notes/{noteId}

***** For sample reports please Open the below *****

--> Serenity Rest BDD reports
Please open "Reports" Folder present in the Project Folder
1) Navigate to "serenity" folder and launch "index.html" using a browser (Use chrome for better look and feel)
2) Within the "serenity" folder and launch "serenity-summary.html" using a browser (Use chrome for better look and feel)

--> Serenity Rest BDD reports
   Please open "Reports" Folder present in the Project Folder
1) Navigate to "gatling" --> "notessimulation-20220921114853845" folder and launch "index.html" using a browser (Use chrome for better look and feel)


