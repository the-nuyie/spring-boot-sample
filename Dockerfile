FROM openjdk:17-alpine
# MAINTAINER baeldung.com

USER root

# Install Curl command
RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

RUN mkdir /.aws

COPY target/spring-boot-sample.jar spring-boot-sample.jar
ENTRYPOINT ["java","-jar","/spring-boot-sample.jar"]
