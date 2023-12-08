#
# Build stage
#
FROM maven
WORKDIR /service
COPY pom.xml .
COPY src ./src

# build the app and download dependencies only when these are new (thanks to the cache)
RUN --mount=type=cache,target=/root/.m2  mvn clean package -X

#
# Package stage
#
FROM eclipse-temurin
WORKDIR /service
COPY target/APIGateway-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "APIGateway-0.0.1-SNAPSHOT.jar"]
