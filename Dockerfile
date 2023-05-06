FROM openjdk:17-alpine
# MAINTAINER baeldung.com
COPY target/spring-boot-sample.jar spring-boot-sample.jar
ENTRYPOINT ["java","-jar","/spring-boot-sample.jar"]
