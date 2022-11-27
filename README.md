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
  2. duration: This specifies for how long irrigation should run
  3. amountOfWater: This specifies the amount of water that should be used to irrigate for the given duration.

Once the trigger time is reached, the sensor device's API is invoked to carry out the irrigation on the plot of land. A retry mechanism is implemented to handle the case where the device is unavailable. In such situations, the system automatically retries a maximum of 3 times using a retry interval of 30 seconds (this can be changed or configured).

For every successful invocation of the sensor device, the execution information is written to the `irrigation_executions` table.

# Database Tables
`lands`: Keeps track of registered lands and their information.  
`water_configs`: Keeps track of the water configuration of lands.  
`irrigation_executions`: Keeps track of times when irrigation took place and for which land.


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

# Running the application
There are two ways to run the application:

```
mvn clean package -DskipTests && java -DUSERNAME=<YOUR USERNAME> -DPASSWORD=<YOUR PASSWORD> -jar target/irrigation-0.0.1-SNAPSHOT.jar
```
OR
```
export USERNAME=<YOUR USERNAME>
export PASSWORD=<YOUR PASSWORD>
mvn spring-boot:run
```
