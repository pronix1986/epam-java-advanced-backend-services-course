package com.epam.sp

import com.epam.sp.configuration.DataSourceConfig
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.assertj.AssertableApplicationContext
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import javax.sql.DataSource
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


// Task 2. Test
class ConfigurationTest {
    private lateinit var contextRunner: ApplicationContextRunner

    @BeforeTest
    fun setUp() {
        contextRunner = ApplicationContextRunner()
    }

    @Test
    fun context_ShouldConfigureCustomDS_WithDefaultConfiguration() {
        contextRunner
            .withUserConfiguration(DataSourceConfig::class.java)
            .run { context: AssertableApplicationContext ->
                assertThat(context).hasSingleBean(DataSource::class.java)
                val dsName = context.getBeanNamesForType(DataSource::class.java)[0]
                assertEquals("myCustomDataSourceConfig", dsName)
            }
    }

    @Test
    fun context_ShouldConfigureDefaultDS_WithCustomDsIsExplicitlyTurnedOff() {
        contextRunner
            .withPropertyValues("app.datasource.enabled=false")
            .withUserConfiguration(DataSourceConfig::class.java)
            .run { context: AssertableApplicationContext ->
                assertThat(context).doesNotHaveBean(DataSource::class.java)
            }
    }
}