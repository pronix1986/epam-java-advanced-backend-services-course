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
class CustomActuatorEndpointTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    // Task 4.4. Add a new custom actuator endpoint(using @Component and @Endpoint(id = ...))
    // to return active profile and current DB url.
    @Test
    fun appInfoActuatorEndpoint_ShouldProduceProfileAndDbUrlInfo_ForTestProfile() {

        mockMvc.perform(
            get("/actuator/appinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.active-profiles").value("[test]"))
            .andExpect(jsonPath("\$.current-db-url").value("jdbc:h2:file:/data/test"))
    }
}