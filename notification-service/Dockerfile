FROM eclipse-temurin:21-jre

WORKDIR /app

ARG JAR_FILE=target/notification-service-*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
