# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -DskipTests dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -DskipTests package

RUN bash -lc 'ls -1 target/*-SNAPSHOT.jar || ls -1 target/*.jar' > /tmp/jarname.txt

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
