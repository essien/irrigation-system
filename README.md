
Land ID is supplied by caller because the sensor device needs to be able to identify what land to irrigate.


# Run tests
mvn clean test

# Run application
mvn clean package -DskipTests && java -DUSERNAME=<YOUR USERNAME> -DPASSWORD=<YOUR PASSWORD> -jar target/irrigation-0.0.1-SNAPSHOT.jar
OR
export USERNAME=<YOUR USERNAME>;export PASSWORD=<YOUR PASSWORD>;mvn spring-boot:run


Install MySQL 8