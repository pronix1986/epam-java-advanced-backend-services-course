plugins {
    id("java")
    id("com.google.protobuf")
}

repositories {
    mavenCentral()
}

dependencies {
    val grpcVersion: String by project
    val tomcatAnnotationVersion: String by project

    compileOnly("org.apache.tomcat:annotations-api:$tomcatAnnotationVersion")
    implementation("io.grpc:grpc-services:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-netty:$grpcVersion")

    testImplementation(project(":task2:grpc:server-java"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.2"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.61.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}