FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY build/libs/Auth2-0.0.1-SNAPSHOT.jar app.jar
COPY .env .env
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]