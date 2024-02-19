package com.epam.sp

import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.AvroDoc
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy
import io.confluent.kafka.streams.serdes.avro.GenericAvroDeserializer
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerializer
import kotlinx.serialization.Serializable
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

const val TOPIC = "dummy"
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(topics = [TOPIC])
@DirtiesContext
class MainIT {
    @Autowired
    private lateinit var embeddedKafkaBroker: EmbeddedKafkaBroker

    private lateinit var producer: KafkaProducer<GenericRecord, GenericRecord>
    private lateinit var consumer: KafkaConsumer<GenericRecord, GenericRecord>

    private val scope = "schema-registry"
    private val schemaRegistryUrl = "mock://$scope"

    @BeforeTest
    fun setUp() {
        // Task 2.2.1. In this task, you need to create a Kafka producer and consumer using Avro to serialize messages.
        producer = KafkaProducer(
            KafkaTestUtils.producerProps(embeddedKafkaBroker).apply {
                this[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = GenericAvroSerializer::class.java
                this[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = GenericAvroSerializer::class.java
                this[AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryUrl
                this[AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY] = TopicRecordNameStrategy().javaClass.name
            }
        )
        consumer = KafkaConsumer(
            KafkaTestUtils.consumerProps("test.group", "false", embeddedKafkaBroker).apply {
                this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = GenericAvroDeserializer::class.java
                this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = GenericAvroDeserializer::class.java
                this[AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryUrl
            }
        )
        consumer.subscribe(listOf(TOPIC))
    }

    @AfterTest
    fun cleanUp() {
        MockSchemaRegistry.clear()
    }

    // Task 2.2.4. Start Kafka, create a topic, and run consumer and producer.
    @Test
    @DirtiesContext
    fun kafka_ShouldRegisterSchemas_WhenRecordsOfDifferentTypesAreProduced() {
        val demoKeyOne = DemoComplexKey(1)
        val demoValueOne = DemoComplexValueOne(0, "dummy")
        val demoKeyTwo = DemoComplexKey(2)
        val demoValueTwo = DemoComplexValueTwo(true, "dummy")

        val keyGenericRecordOne = Avro.default.toRecord(DemoComplexKey.serializer(), demoKeyOne)
        val valueGenericRecordOne = Avro.default.toRecord(DemoComplexValueOne.serializer(), demoValueOne)
        val keyGenericRecordTwo = Avro.default.toRecord(DemoComplexKey.serializer(), demoKeyTwo)
        val valueGenericRecordTwo = Avro.default.toRecord(DemoComplexValueTwo.serializer(), demoValueTwo)

        // Task 2.2.2. Create a simple Kafka producer that sends a simple message to a topic serializing it using Avro.
        producer.send(ProducerRecord(TOPIC, keyGenericRecordOne, valueGenericRecordOne)) { _, e ->
            if (e != null) fail("Producer Error: $e")
        }
        // Task 2.2.3. Create a simple Kafka consumer which listens to a topic for a message, deserializes the message, and prints it.
        val firstConsumerRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC)
        println("Consumer generic record: $firstConsumerRecord")
        val genericRecordValue = firstConsumerRecord.value()
        val deserializedRecordValue: DemoComplexValueOne = Avro.default.fromRecord(DemoComplexValueOne.serializer(), genericRecordValue)
        println("Consumer record: $deserializedRecordValue")

        val mockSchemaRegistryClient = MockSchemaRegistry.getClientForScope(scope)
        val allRegisteredSubjects = mockSchemaRegistryClient.allSubjects

        // Two schemas should be registered:
        // For key default TopicNameStrategy is used, so the subject is dummy-key
        // For value, TopicRecordNameStrategy was explicitly set, so the subject is dummy-com.epam.sp.DemoComplexValueOne
        assertEquals(2, allRegisteredSubjects.size)
        assertTrue(allRegisteredSubjects.contains("dummy-key"))
        assertTrue(allRegisteredSubjects.contains("dummy-com.epam.sp.DemoComplexValueOne"))

        // Task 2.2.5. Try to use different Avro schemas for serialization and deserialization. Observe that
        //schema version/id has changed e.g. in schema registry and kafka message payload(bytes 1-4).
        // ...
        // For this task, produce another message with different value schema
        producer.send(ProducerRecord(TOPIC, keyGenericRecordTwo, valueGenericRecordTwo)) { _, e ->
            if (e != null) fail("Producer Error: $e")
        }
        val secondConsumerRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC)
        println("Consumer generic record: $secondConsumerRecord")
        // One more schema is registered with a key dummy-com.epam.sp.DemoComplexValueTwo
        val updatedAllRegisteredSubjects = mockSchemaRegistryClient.allSubjects
        assertEquals(3, updatedAllRegisteredSubjects.size)
        assertTrue(updatedAllRegisteredSubjects.contains("dummy-com.epam.sp.DemoComplexValueTwo"))
        assertEquals("com.epam.sp.DemoComplexValueOne", mockSchemaRegistryClient.getSchemaById(2).name())
        assertEquals("com.epam.sp.DemoComplexValueTwo", mockSchemaRegistryClient.getSchemaById(3).name())
        // So that, the schemas are different in a schema registry

        // check the message payloads
        val serializer = GenericAvroSerializer().apply {
            this.configure(
                mutableMapOf(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl),
                false)
        }
        val firstRecordFiveBytes = serializer.serialize(TOPIC, firstConsumerRecord.value()).slice(0..<5)
        val secondRecordFiveBytes = serializer.serialize(TOPIC, secondConsumerRecord.value()).slice(0..<5)
        println("$firstRecordFiveBytes VS $secondRecordFiveBytes")
        assertFalse(firstRecordFiveBytes.toTypedArray().contentDeepEquals(secondRecordFiveBytes.toTypedArray()))
        // So that, schema ids are different in a payload as well

        producer.close()
        println("Done!")
    }

}

@Serializable
data class DemoComplexKey(
    @AvroDoc("Dummy identifier")
    val id: Int
)

@Serializable
data class DemoComplexValueOne(
    val param1: Int,
    val param2: String
)

@Serializable
data class DemoComplexValueTwo(
    val param3: Boolean,
    val param4: String
)