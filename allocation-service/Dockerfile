FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY . .
RUN mvn -pl allocation-service -am package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/allocation-service/target/allocation-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
