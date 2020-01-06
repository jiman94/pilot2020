FROM java:8
#FROM openjdk:8-jdk
MAINTAINER jiman <ryu.jiman@gmail.com>

VOLUME /tmp

ADD springboot-docker-gradle-1.0.1.jar app.jar


RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
