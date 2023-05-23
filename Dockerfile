FROM openjdk:17-alpine
# MAINTAINER baeldung.com

# Install Curl command
RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

COPY target/spring-boot-sample.jar spring-boot-sample.jar
ENTRYPOINT ["java","-jar","/spring-boot-sample.jar"]
