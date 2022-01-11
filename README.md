# http-feeds-server-spring-boot-starter

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.httpfeeds/http-feeds-server-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.httpfeeds/http-feeds-server-spring-boot-starter)

Spring Boots starter for an [HTTP Feed](http://www.http-feeds.org/).

## Getting Started

Go to [start.spring.io](https://start.spring.io/#!type=maven-project&language=java&packaging=jar&groupId=com.example&artifactId=httpfeeds-server-example&name=httpfeeds-server-example&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.httpfeeds-server-example&dependencies=web,jdbc,h2) and create an new application. Select these dependencies:

- Spring Web (to provide an HTTP endpoint)
- JDBC API (for database connectivity)

for testing, you might also want to add

- H2 Database

Then add this library to your `pom.xml`:

```xml
    <dependency>
      <groupId>org.httpfeeds</groupId>
      <artifactId>http-feeds-server-spring-boot-starter</artifactId>
      <version>0.0.2</version>
    </dependency>
```

The [`HttpFeedServerAutoConfiguration`](src/main/java/org/httpfeeds/server/HttpFeedServerAutoConfiguration.java) adds all relevant beans.


Add these properties to your `application.properties`:

```properties
httpfeed.server.feed=myfeed
httpfeed.server.path=/myfeed
httpfeed.server.limit=1000
httpfeed.server.jdbc.table=feed
```

Next, make sure to have a valid schema for you database set up (use [Flyway](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-use-a-higher-level-database-migration-tool) or the [schema.sql](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-initialize-a-database-using-spring-jdbc) file):

```sql
create table feed
(
    id       varchar(1024) primary key,
    type     varchar(1024),
    source   varchar(1024),
    time     timestamp,
    subject  varchar(1024),
    method   varchar(1024),
    data     clob
);
```

and make sure your database is connected in your `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
```

Finally, make sure that your application adds new feed items by calling the `FeedItemAppender#append` method.

```java
feedItemAppender.append(
    "org.http-feeds.example.inventory",
    "https://example.http-feeds.org/inventory", 
    Instant.now(), 
    subject, 
    null, 
    data);
```

When you start the application, you can connect to http://localhost:8080/myfeed.

## Security

Basic Auth is optionally supported.

Add the `spring-boot-starter-security` dependency to your `pom.xml`:

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```

and specify a username and password in your `application.properties`:

```properties
# alice:secure123
httpfeed.server.credentials[0].username=alice
httpfeed.server.credentials[0].password={bcrypt}$2a$10$WWJ/p6BOga2R5TRb2LIy4OzlPNiwNM0/aikVKuQ74dKgs67xLIeGS
```

The password should be encoded, e.g. with [BCrypt](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html).


