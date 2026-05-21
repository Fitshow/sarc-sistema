FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY . .
RUN mvn -pl schedule-service -am package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/schedule-service/target/schedule-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
