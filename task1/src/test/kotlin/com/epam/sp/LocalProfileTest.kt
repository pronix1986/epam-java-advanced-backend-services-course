package com.epam.sp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test
import kotlin.test.assertEquals


@SpringBootTest
@ComponentScan("com.epam.sp")
@ActiveProfiles("local")
class LocalProfileTest(@Autowired private val env: Environment) {

    @Test
    fun actuatorEndpoint_ShouldProduceBasicDetails_WithDefaultConfiguration() {
        assertEquals("jdbc:h2:file:./data/local", env.getProperty("app.datasource.url"))
    }
}