FROM amazoncorretto:8
LABEL authors="Emmanuel Essien-nta <colourfulemmanuel@gmail.com>"
ADD target/irrigation-0.0.1-SNAPSHOT.jar irrigation-service.jar
ENTRYPOINT ["java", "-jar", "/irrigation-service.jar"]
