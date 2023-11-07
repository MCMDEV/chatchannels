plugins {
    id("java")
    kotlin("jvm") version "1.9.20-Beta2"
}

repositories {
    mavenCentral()
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
kotlin {
    jvmToolchain(17)
}