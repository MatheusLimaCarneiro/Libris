FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17 AS run
LABEL maintainer="matheuslimacarneirors@gmail.com"
WORKDIR /app
COPY --from=build /build/target/redeSocial-0.0.1-SNAPSHOT.jar ./libris-back.jar
ENTRYPOINT ["java", "-jar", "libris-back.jar"]

EXPOSE 8080