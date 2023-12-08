FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY src/main/resources/binaries/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
