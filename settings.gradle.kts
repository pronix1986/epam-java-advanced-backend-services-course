rootProject.name = "epam-java-advanced-backend-services-course"
pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion // solve no-arg exception for data classes
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
include("task1")
include("task2")
include("task2:grpc")
findProject(":task2:grpc")?.name = "grpc"
include("task2:grpc:server-java")
findProject(":task2:grpc:server-java")?.name = "server-java"
include("task2:grpc:client-java")
findProject(":task2:grpc:client-java")?.name = "client-java"
include("task2:grpc:client-kotlin")
findProject(":task2:grpc:client-kotlin")?.name = "client-kotlin"
