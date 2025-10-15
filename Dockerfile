# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -q -DskipTests dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -q -DskipTests package


# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd -r -u 1001 spring && chown -R spring:spring /app
USER spring

COPY --from=build /tmp/jarname.txt /tmp/jarname.txt
RUN JAR=$(cat /tmp/jarname.txt) && echo "Using JAR: $JAR" && cp "$JAR" /app/app.jar

EXPOSE 8080
ENV SERVER_PORT=8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
