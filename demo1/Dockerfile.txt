FROM openjdk:11

LABEL maintainer="sss11"

# ARG TAR_FILE=target/*.jar
# RUN mkdir -p /apps/
# ADD ${TAR_FILE} /apps/apps/

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /demo1.jar
RUN mkdir -p /config

EXPOSE 8080

ENV v2=v-50-50-unhealthy


ENTRYPOINT [ "sh", "-c", "printenv && java $JAVA_OPTS -jar /demo1.jar" ]
###