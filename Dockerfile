FROM maven:4.0.0-rc-5-amazoncorretto-21-debian AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn clean install -DskipTests

FROM amazoncorretto:21.0.9-al2023-headless
WORKDIR /app
COPY --from=build /app/target/quarkus-app/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]