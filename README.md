# Movie-Ticket-Reservation – backend

Movie ticket reservation and management backend application build using [Spring Boot 3](https://docs.spring.io/spring-boot/index.html) and [Java 17](https://docs.oracle.com/en/java/javase/17/). This project represents backend application used for processing and managing movie ticket reservation process.

This is project is backend application for [movie-ticket-reservation-client](https://github.com/lmicovic/movie-ticket-reservation-client) application.

## Features

- **Easy startup:** project is auto configured and plug-and-play, ready to run. No need for additional settings.

- **Comprehensive:** contains all basic and advanced features required to respond to movie ticket reservation process.

- **Integrity:** code is logically divided and easily understandable.

- **Secure:** using security features that secure important communication with authenticated user. Also provide simple features to unauthorized user movie previews.


## Technical Features

- Project is built entirely using [Spring Boot 3](https://docs.spring.io/spring-boot/index.html) and [Java 17](https://docs.oracle.com/en/java/javase/17/).

- Project is using required dependencies through [Maven](https://maven.apache.org/guides/) project management tool.

- Communicating with database using [Spring JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) and [Hibernate](https://hibernate.org/orm/documentation/6.5/).

- Storing permanent data to [H2](https://h2database.com/) in-memory database.

- Project at startup create and fill database tables with initial data in Application.java class.

- Implements [Spring Security](https://docs.spring.io/spring-security/reference/index.html) for security features like user authentication ,generating and validation [JWT Tokens](https://jwt.io/introduction). Uses JWT Token to authenticate and manage users.

- All HTTP Requests and Responses consume JSON format in HTTP body.

- Backend Application is running on url: [http://localhost:8080/](http://localhost:8080/)

- Database console is running on url: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)



##  Important endpoints

- [http://localhost:8080/](http://localhost:8080/) - root endpoint url.

- [http://localhost:8080/user](http://localhost:8080/user) – crud operations required to work with users entities.

- [http://localhost:8080/auth](http://localhost:8080/auth) – crud operations required to authenticate current user.

- [http://localhost:8080/movie](http://localhost:8080/movie) - crud operations required to work with movies entities.

- [http://localhost:8080/reservation](http://localhost:8080/reservation) - crud operations required to work with movie reservations.

- [http://localhost:8080/projection](http://localhost:8080/projection) - crud operations required to work with movies projections.

- [http://localhost:8080/comment](http://localhost:8080/comment) - crud operations required to work with movie reservations.

- [http://localhost:8080/room](http://localhost:8080/room) - crud operations required to work with room entities.


## Maven Dependencies

Maven dependencies are specified in pom.xml file.

-  Parent Spring Boot Project: [org.springframework.boot spring-boot-starter-parent 3.2.3](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent/3.2.3)
- Spring Boot Starter Web: [org.springframework.boot spring-boot-starter-web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- Spring Boot JDBC: [org.springframework.boot spring-boot-starter-datajdbc](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jdbc)
- Spring Boot JPA: [org.springframework.boot spring-boot-starter-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
-  Spring Boot Security: [org.springframework.boot spring-boot-starter-security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
- H2 Database: [com.h2database h2 runtime](https://mvnrepository.com/artifact/com.h2database/h2)



## Setup

Project is auto configured and ready to start after download. No need for additional settings. Project is developed using [Eclipse IDE](https://www.eclipse.org/documentation/).

To change database type it can be configured in application.properties with addition of required Maven dependencies for certain database in Maven pom.xml file.

To disable [Spring Security](https://docs.spring.io/spring-security/reference/index.html) write following property in application.properties file:

    custom.variables.SpringSecurity.enable=false

To enable Spring Security write following command in application.properties file:

    custom.variables.SpringSecurity.enable=true


***NOTE:** Other contributions are welcome this is just a main functionalities for Movie Ticket Reservation system, lots of things are available to implement further.
