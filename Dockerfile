FROM eclipse-temurin:21.0.1_12-jre-jammy
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar /app/app.jar
COPY src/main/resources/keystore.jks /app/keystore.jks
COPY src/main/resources/truststore.jks /app/truststore.jks
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]