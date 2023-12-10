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
ARG JAR_FILE=/usr/app/target/TasksMS-0.0.1-SNAPSHOT.jar
COPY --from=build $JAR_FILE /app/TasksMS-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar /app/TasksMS-0.0.1-SNAPSHOT.jar
