# 1. try:
##
## Build stage
##
#FROM maven AS build
#WORKDIR /service
#COPY pom.xml .
#COPY src ./src
#
## build the app and download dependencies only when these are new (thanks to the cache)
#RUN --mount=type=cache,target=/root/.m2  mvn clean package -X
#
##
## Package stage
##
#FROM eclipse-temurin
#WORKDIR /service
#COPY --from=build target/APIGateway-0.0.1-SNAPSHOT.jar .
#ENTRYPOINT ["java", "-jar", "APIGateway-0.0.1-SNAPSHOT.jar"]


# 2. try:
#
# Build stage
#
FROM eclipse-temurin AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin
ARG JAR_FILE=/usr/app/target/APIGateway-0.0.1-SNAPSHOT.jar
COPY --from=build $JAR_FILE /app/APIGateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar /app/APIGateway-0.0.1-SNAPSHOT.jar
