FROM maven:4.0.0-rc-5-amazoncorretto-21-debian AS build
WORKDIR /app
COPY feedback-api/src /app/feedback-api/src
COPY feedback-api/pom.xml /app/feedback-api
COPY pom.xml /app
RUN mvn clean install -DskipTests

FROM amazoncorretto:21.0.9-al2023-headless
WORKDIR /app
COPY --from=build /app/feedback-api/target/quarkus-app /app
EXPOSE 8080
CMD ["java", "-jar", "quarkus-run.jar", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.http.port=${PORT}"]