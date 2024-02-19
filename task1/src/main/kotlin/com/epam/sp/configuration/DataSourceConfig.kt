package com.epam.sp.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration(proxyBeanMethods = false)
class DataSourceConfig {
    // Task 1.1. Introduce a configuration(using @Configuration)
    // which should have a method annotated with @Bean which returns a configured data source instance.

    // Task 2.1. Custom Data Source should be optionally enabled or disabled
    // based on a configuration property (@ConditionalOnProperty).
    // Task 2.2. Enable the custom Data Source if the property is set to true or missing
    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "app.datasource", name = ["enabled"], havingValue = "true", matchIfMissing = true)
    @ConfigurationProperties(prefix = "app.datasource")
    fun myCustomDataSourceConfig(): DataSource =
        DataSourceBuilder.create().build();

}