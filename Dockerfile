FROM openjdk:17-jdk-slim
COPY target/mywebsite-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]