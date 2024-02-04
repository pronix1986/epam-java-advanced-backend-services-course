package com.epam.sp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.epam.sp")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SimpleActuatorTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    // Task 4.3. Check OOTB actuator endpoints (using /actuator)
    @Test
    fun actuatorEndpoint_ShouldProduceBasicDetails_WithDefaultConfiguration() {
        // actuator endpoint does not support 'hal'
        // testing with Traverson is not possible
        mockMvc.perform(
            get("/actuator/health")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.status").value("UP"))
    }
}