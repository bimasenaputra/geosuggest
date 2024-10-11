FROM openjdk:17-jdk-alpine
MAINTAINER bimasenaputra@gmail.com
COPY build/libs/geosuggest-0.0.1-SNAPSHOT.jar geosuggest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/geosuggest-0.0.1-SNAPSHOT.jar"]