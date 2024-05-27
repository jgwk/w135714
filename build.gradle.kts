plugins {
    id("java-library")
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.20")
        compileOnly("io.vertx:vertx-codegen:4.0.0")

        annotationProcessor("org.projectlombok:lombok:1.18.20")
        annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

        implementation("org.slf4j:slf4j-api:1.7.36")
        implementation("org.slf4j:jcl-over-slf4j:1.7.36")
        implementation("ch.qos.logback:logback-core:1.2.3")
        implementation("ch.qos.logback:logback-classic:1.2.3")

        implementation("org.mapstruct:mapstruct:1.5.5.Final")

        implementation("io.vertx:vertx-core:4.0.0")
        implementation("io.vertx:vertx-web:4.0.0")

        implementation("com.google.guava:guava:33.2.0-jre")

        testImplementation("junit:junit:4.13.2")
    }

}
