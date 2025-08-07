# Usa una imagen base de Java
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

RUN ls -la /app/target

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar /app/PruebaPase.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/PruebaPase.jar"]
