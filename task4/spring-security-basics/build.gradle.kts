plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.epam.sp"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

    val springBootVersion: String by project
    val kotlinVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.jayway.jsonpath:json-path:2.9.0")

    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    runtimeOnly("com.h2database:h2:2.2.224")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}