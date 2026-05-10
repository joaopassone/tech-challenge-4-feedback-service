FROM maven:4.0.0-rc-5-amazoncorretto-21-debian AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn package -Dnative

FROM registry.access.redhat.com/ubi9/ubi-minimal:9.7
WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
COPY --from=build target/*-runner /work/application

EXPOSE 8080
USER 1001

ENTRYPOINT ["./application", "-Dquarkus.http.host=0.0.0.0"]