
plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "org.example.Main")
    }
    from({
        configurations.runtimeClasspath.get().filter { it.exists() }.map { if (it.isDirectory) it else zipTree(it) }
    }) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

tasks.test {
    useJUnitPlatform()
}