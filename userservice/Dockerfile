# Multi-stage build: compile with Maven, run with a lightweight JRE.

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render assigns a dynamic port via $PORT — bind to it, falling back
# to 8081 for local runs where $PORT isn't set.
# Memory caps applied upfront (learned from Keycloak's OOM crashes on
# Render's 512MB free tier) — Spring Boot apps are lighter than Keycloak,
# so these limits have more headroom than Keycloak needed.
ENTRYPOINT ["sh", "-c", "java -Xms64m -Xmx300m -XX:MaxMetaspaceSize=150m -jar app.jar --server.port=${PORT:-8081}"]
