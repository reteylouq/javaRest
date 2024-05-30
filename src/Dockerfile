FROM openjdk:11-jdk-slim
VOLUME /tmp
COPY target/restaurant-reservation-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
