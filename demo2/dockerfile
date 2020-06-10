FROM openjdk:11

LABEL maintainer="sss11"


ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /tmp/demo2.jar

EXPOSE 8080 


ENTRYPOINT [ "sh", "-c", "java -jar /tmp/demo2.jar" ]