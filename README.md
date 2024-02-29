# epam-java-advanced-backend-services-course
https://learn.epam.com/study/path?rootId=8748692

# Task 1.1 - Spring Configuration
Create Basic Spring application which will have Data Source configuration provided by spring Configuration (2 points):
Introduce a configuration(using @Configuration) which should have a method annotated with @Bean which returns a configured data source instance.
Use https://mvnrepository.com/artifact/com.h2database/h2 as a Data Source.
Add test which tests your application by saving an entity to the data source(@DataJpaTest/@DataJdbcTest).

# Solution - Task 1.1
Custom DataSource > `com.epam.sp.configuration.DataSourceConfig`. Config is externalized in `app.datasource.*` properties
Test is added `SimpleJpaTest`

# Task 1.2 - Conditional Configuration
Adjust existing configuration for Data Source based on conditional properties (2 points):
Custom Data Source should be optionally enabled or disabled based on a configuration property (@ConditionalOnProperty).
Enable the custom Data Source if the property is set to true or missing.
(Optional) Disable the custom Data Source and discover which Auto Configuration is responsible for creating a default Data source.

# Solution - Task 1.2
Custom DS can be turned on/off based on `app.datasource.enabled` property
Test is added `ConfigurationTest`

# Task 1.3 - Spring Profiles
Separate Data Source Configurations using Profiles (2 points):
Create a configuration files for two environments (local, dev).
Define database connection properties (url, username, password) in each file.
Create additional configuration file for test profile (under /test/java/resources folder).
Adjust test from the Task 1 to use Data Source configured from test configuration file(@DataJpaTest/@DataJdbcTest + @AutoConfigureTestDatabase(replace = NONE)).

# Solution - Task 1.3
Extra configuration files were added on top of a default one: `application-dev/local/test` with different dataSource settings
Added test `LocalProfileTest`

# Task 1.4 - Spring Actuator
Enable actuator by adding https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator dependency. (1 point)
Expose Default Spring Actuator by configuring SecurityFilterChain/SecurityWebFilterChain bean. (1 point)
Check OOTB actuator endpoints (using /actuator).
Add a new custom actuator endpoint(using @Component and @Endpoint(id = ...)) to return active profile and current DB url. (2 points)
Only 2 endpoints (/health and custom endpoint above) should be exposed. Apply these changes for both environments (local and dev) by using a base configuration file.

# Solution - Task 1.4
Enabled and exposed actuator (`SimpleActuatorTest` test, `SecurityConfig` Spring Security configuration). Created a custom endpoint `AppInfoEndpoint`

# Task 2.1 - gRPC part
Create a simple gRPC server application that listens on port 8080 for a request like this...
Create a simple Java gRPC client application that sends a request to the simple server (share the same proto file), gets a response, and prints it.
Create a simple gRPC client application in any other language you like (or ask a trainer/lector for a Python example) that sends a request to the simple server, gets a response, and prints it. Use the same proto file.
Start a server and try to communicate with it using the clients you just wrote.

# Solution - Task 2.1
Created `pingpong.proto` with messages and a service declarations. Protobuf gradle plugin is required to trigger 'generateProtobuf' task.
Created a module :grpc:server-java which start gRPC server on port 8080.
Created modules :grpc:client-java and :grpc:client-kotlin with gRPC clients in two different languages in a form of unit tests.


# Task 2.2 - Avro part
In this task, you need to create a Kafka producer and consumer using Avro to serialize messages.
Create a simple Kafka producer that sends a simple message to a topic serializing it using Avro.
Create a simple Kafka consumer which listens to a topic for a message, deserializes the message, and prints it.
Start Kafka, create a topic, and run consumer and producer.
Try to use different Avro schemas for serialization and deserialization. Observe that
schema version/id has changed e.g. in schema registry and kafka message payload(bytes 1-4).

# Solution - Task 2.1
Created :avro module with the dummy Spring configuration and a test class with all the implementation details.
GenericRecords are used purely for testing purposes.
Value topic naming strategy is changed from the default to TopicRecordNamingStrategy which is normally used when the same topic holds the values of different types.
Since Kotlin is used as a programming language, so that kotlinx-serialization provides a serialization mechanisms and Avro4k is used specifically to operate with Avro.


# Task 3
... A lot of stuff to do. See 'learn' portal

# Solution - Task3
Created a dedicated README.md under task3 module


