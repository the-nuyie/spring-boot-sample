FROM openjdk:17-alpine
# MAINTAINER baeldung.com
COPY target/spring-boot-sample-0.0.1-SNAPSHOT.jar spring-boot-sample-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/spring-boot-sample-0.0.1-SNAPSHOT.jar"]
