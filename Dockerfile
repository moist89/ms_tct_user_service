FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21

WORKDIR /app

COPY --from=builder /app/target/ms_tct_user_service-*.jar app.jar

EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]