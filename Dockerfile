# syntax=docker/dockerfile:1.7

# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Use the Maven Wrapper
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw

# Copy POM first to warm dependency cache
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -q -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src

# Build and stash the fat jar at a fixed location
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -q -DskipTests package && \
    JAR="$(ls -1 target/*-SNAPSHOT.jar 2>/dev/null || ls -1 target/*.jar | head -n 1)" && \
    echo "Found JAR: $JAR" && \
    cp "$JAR" /tmp/app.jar

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Run as non-root
RUN useradd -r -u 1001 spring && chown -R spring:spring /app
USER spring

# Copy the jar produced in the build stage
COPY --from=build /tmp/app.jar /app/app.jar

EXPOSE 8080
ENV SERVER_PORT=8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
