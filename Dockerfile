FROM maven:3.8-openjdk-17-slim as builder
RUN mkdir /usr/src/app
WORKDIR /usr/src/app
COPY . .
RUN --mount=target=/root/.m2/repository,type=cache mvn --batch-mode --update-snapshots -Dmaven.test.skip package

FROM openjdk:11-jre-slim
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN mkdir /app
COPY --from=builder /usr/src/app/target/db-spring-template.jar /app
EXPOSE 8080/tcp
CMD ["java", "-jar", "/app/db-spring-template.jar"]
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
            CMD curl --fail --silent http://localhost:8080/actuator/health | grep UP
