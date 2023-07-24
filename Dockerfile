FROM maven:3.8.3-openjdk-17 as builder
MAINTAINER Rigoberto Perez
WORKDIR /workdir
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
COPY --from=builder /workdir/target/console-*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar","app.jar", "--server.port=8090"]