FROM maven:3.6.1-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/

WORKDIR /tmp/
RUN mvn package

FROM fabric8/java-alpine-openjdk8-jdk
EXPOSE 7000
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/scanner-1.0.jar /deployments/app.jar