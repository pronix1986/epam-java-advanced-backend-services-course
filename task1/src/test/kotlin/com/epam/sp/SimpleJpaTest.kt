package com.epam.sp

import com.epam.sp.jpa.entities.Order
import com.epam.sp.jpa.repositories.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test
import kotlin.test.assertEquals

// Task 3.4. Adjust test from the Task 1 to use Data Source
// configured from test configuration file(@DataJpaTest/@DataJdbcTest + @AutoConfigureTestDatabase(replace = NONE))

//@DataJpaTest - the annotation was replaced with @SpringBootTest, otherwise it doesn't work well (if at all) with WebMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@ComponentScan("com.epam.sp")
class SimpleJpaTest(
    @Autowired val orderRepository: OrderRepository) {

    // Task 1.3. Add test which tests your application
    // by saving an entity to the data source(@DataJpaTest/@DataJdbcTest).
    @Test
    fun orderRepository_ShouldSaveOrderEntity_WhenEmbeddedDataSourceIsProvided() {
        // Given
        val testOrder = Order(1, "test")

        // When
        orderRepository.save(testOrder)

        // Then
        assertEquals(1, orderRepository.count())
        assertEquals("test", orderRepository.findById(1L).get().name)
    }
}