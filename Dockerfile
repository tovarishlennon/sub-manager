FROM eclipse-temurin:17-jre

ARG JAR_FILE=target/sub-manager-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]