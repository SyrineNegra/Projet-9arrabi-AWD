# Multi-stage build pour le microservice Reservation
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier les fichiers pom et code source
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Image d'exécution légère
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copier le JAR généré
COPY --from=build /app/target/reservation-*.jar app.jar

# Port d'écoute du microservice
EXPOSE 8086

# Démarrer le microservice
ENTRYPOINT ["java", "-jar", "app.jar"]
