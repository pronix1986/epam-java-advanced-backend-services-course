plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

dependencies {
    val springBootVersion: String by project
    val confluentVersion: String by project
    val springKafkaVersion: String by project
    val avro4kVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.springframework.kafka:spring-kafka:$springKafkaVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.springframework.kafka:spring-kafka-test:$springKafkaVersion")
    implementation("io.confluent:kafka-streams-avro-serde:$confluentVersion")
    testImplementation("com.github.avro-kotlin.avro4k:avro4k-core:$avro4kVersion")



}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}