FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY . .
RUN mvn -pl user-service -am package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/user-service/target/user-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
