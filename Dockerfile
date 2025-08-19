FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /build
COPY . .
RUN mvn -B clean package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/target/Balance-Batch-0.0.2-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

