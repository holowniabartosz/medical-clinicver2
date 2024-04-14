FROM openjdk:17

WORKDIR /app

COPY ./target/medical-clinic-0.0.1-SNAPSHOT.jar /app/medical-clinic.jar

CMD ["java", "-jar", "medical-clinic.jar"]