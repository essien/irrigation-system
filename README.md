# Overview

This is an application that manages the irrigation of plots of land. It supports four endpoints:
  - Add a plot of land: Allows you to register a plot of land with a given ID - called the land ID.
  - Edit a plot of land: Allows you to edit the area of the land.
  - Configure a plot of land: Allows you to configure the land. More details below.
  - Get all plots of land: Allows you to retrieve registered plots of lands in paginated form.

In particular, the endpoint that configures a plot of land accepts time schedules for the irrigation of a plot of land.
Each time schedule consists of:
  1. cron expression: This specifies when irrigation should be triggered. The cron expressions
  can be validated using this resource: https://www.freeformatter.com/cron-expression-generator-quartz.html
  2. duration: This specifies for how long (in minutes) irrigation should run.
  3. water quantity: This specifies the amount of water that should be used to irrigate for the given duration.

Once the trigger time is reached, the sensor device's API is invoked to carry out the irrigation on the plot of land. A retry mechanism is implemented to handle the case where the device is unavailable. In such situations, the system automatically retries a maximum of 3 times using a retry interval of 30 seconds (this can be changed or configured).

For every successful invocation of the sensor device, the execution information is written to the `irrigation_executions` table.

# Database Tables
The schema for application's database tables can be found [here](https://github.com/essien/irrigation-system/blob/master/src/main/resources/database/schema.sql).

__Description of Tables__  
`lands`: Keeps track of registered lands and their information.  
`water_configs`: Keeps track of the water configuration of lands.  
`irrigation_executions`: Keeps track of times when irrigation took place and for which land.

Other database tables are present and used by the quartz scheduler. These can be found [here](https://github.com/essien/irrigation-system/blob/master/src/main/resources/database/quartz_tables.sql). This scheduler can be configured to manage the clustered scheduling of jobs.


# Tech Stack
- Java 8
- MySQL 8
- Maven
- Spring Boot

# Testing the application
A couple of unit tests have been added. Execute the following command to run them all:
```
mvn clean test
```

# Building the application
```
mvn clean package
```

# Running the application

To run the application, your database username and password is required. Following are two ways to run the application:

```
java -DUSERNAME=<YOUR USERNAME> -DPASSWORD=<YOUR PASSWORD> -jar target/irrigation-0.0.1-SNAPSHOT.jar
```
OR
```
export USERNAME=<YOUR USERNAME>
export PASSWORD=<YOUR PASSWORD>
mvn spring-boot:run
```

#### Running with Docker
Execute the following commands to run the application inside a docker container.

```
# Create a docker network so that the application can connect to the MySQL instance
docker network create my-network

# Spin up a MySQL8 instance
docker container run --name mysql-8 --network my-network -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=irrigation -d mysql:8.0.31

# Build the java application if you haven't done so already
mvn clean package -DskipTests

# Build the docker image
docker build . -t irrigation

# Run the irrigation application
docker run --network my-network -e SPRING_DATASOURCE_URL='jdbc:mysql://mysql-8:3306/irrigation?createDatabaseIfNotExist=true&serverTimezone=UTC' -e USERNAME=root -e PASSWORD=password -p 8080:8080 irrigation
```
Once the application is up and running, you should see trailing lines such as the following in the log file
```
2022-11-27 15:40:43.051  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-11-27 15:40:43.060  INFO 1 --- [           main] c.g.e.b.i.IrrigationApplication          : Started IrrigationApplication in 3.987 seconds (JVM running for 4.319)
```

The application will automatically create a database named `irrigation`, and start serving requests on port `8080`.

Sample scripts have been included in the [scripts](https://github.com/essien/irrigation-system/tree/master/scripts) folder for ease of getting started.