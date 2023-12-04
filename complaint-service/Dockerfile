FROM eclipse-temurin:17

WORKDIR /app

COPY target/complaint-service-0.0.1-SNAPSHOT.jar /app/complaint-service.jar

ENTRYPOINT ["java", "-jar", "complaint-service.jar"]