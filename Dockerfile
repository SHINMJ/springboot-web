FROM adoptopenjdk:8-jdk-hotspot AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM adoptopenjdk:8-jdk-hotspot
COPY --from=builder build/libs/*.jar app.jar

ARG SPRING_ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${SPRING_ENVIRONMENT}

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
