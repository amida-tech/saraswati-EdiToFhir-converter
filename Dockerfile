FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} saraswati-x12.jar
ENTRYPOINT ["java","-jar","/saraswati-x12.jar"]
