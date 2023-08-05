FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar Spell-Checker-API-Spring-Boot-0.0.1-SNAPSHOT-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080