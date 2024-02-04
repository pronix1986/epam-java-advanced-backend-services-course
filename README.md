# epam-java-advanced-backend-services-course
https://learn.epam.com/study/path?rootId=8748692

# Task 1 - Spring Configuration
Create Basic Spring application which will have Data Source configuration provided by spring Configuration (2 points):
Introduce a configuration(using @Configuration) which should have a method annotated with @Bean which returns a configured data source instance.
Use https://mvnrepository.com/artifact/com.h2database/h2 as a Data Source.
Add test which tests your application by saving an entity to the data source(@DataJpaTest/@DataJdbcTest).

# Solution - Task 1
Custom DataSource > `com.epam.sp.configuration.DataSourceConfig`. Config is externalized in `app.datasource.*` properties
Test is added `SimpleJpaTest`

# Task 2 - Conditional Configuration
Adjust existing configuration for Data Source based on conditional properties (2 points):
Custom Data Source should be optionally enabled or disabled based on a configuration property (@ConditionalOnProperty).
Enable the custom Data Source if the property is set to true or missing.
(Optional) Disable the custom Data Source and discover which Auto Configuration is responsible for creating a default Data source.

# Solution - Task 2
Custom DS can be turned on/off based on `app.datasource.enabled` property
Test is added `ConfigurationTest`

# Task 3 - Spring Profiles
Separate Data Source Configurations using Profiles (2 points):
Create a configuration files for two environments (local, dev).
Define database connection properties (url, username, password) in each file.
Create additional configuration file for test profile (under /test/java/resources folder).
Adjust test from the Task 1 to use Data Source configured from test configuration file(@DataJpaTest/@DataJdbcTest + @AutoConfigureTestDatabase(replace = NONE)).

# Solution - Task 3
Extra configuration files were added on top of a default one: `application-dev/local/test` with different dataSource settings
Added test `LocalProfileTest`

# Task 4 - Spring Actuator
Enable actuator by adding https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator dependency. (1 point)
Expose Default Spring Actuator by configuring SecurityFilterChain/SecurityWebFilterChain bean. (1 point)
Check OOTB actuator endpoints (using /actuator).
Add a new custom actuator endpoint(using @Component and @Endpoint(id = ...)) to return active profile and current DB url. (2 points)
Only 2 endpoints (/health and custom endpoint above) should be exposed. Apply these changes for both environments (local and dev) by using a base configuration file.

# Solution - Task 4
Enabled and exposed actuator (`SimpleActuatorTest` test, `SecurityConfig` Spring Security configuration). Created a custom endpoint `AppInfoEndpoint`



