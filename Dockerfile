FROM openjdk:17-alpine
# MAINTAINER baeldung.com

USER root

# Install Curl command
RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

RUN mkdir /.aws
RUN mkdir /spring-config

COPY target/spring-boot-sample.jar spring-boot-sample.jar
COPY src/main/resources/application.properties /spring-config/application.properties

ENTRYPOINT ["java","-jar","spring-boot-sample.jar", "--spring.config.location=file:///spring-config/application.properties"]
