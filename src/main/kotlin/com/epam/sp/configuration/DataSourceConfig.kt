package com.epam.sp.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    // Task 1.1. Introduce a configuration(using @Configuration)
    // which should have a method annotated with @Bean which returns a configured data source instance.
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    fun myCustomDataSourceConfig() =
        DataSourceBuilder.create().build();

}