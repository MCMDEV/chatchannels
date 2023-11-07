plugins {
    id("java")
    id("checkstyle")
    id("application")
    id("io.papermc.paperweight.userdev") version Deps.Plugins.paperweightVersion
    id("xyz.jpenilla.run-paper") version Deps.Plugins.runPaperVersion
    id("net.minecrell.plugin-yml.paper") version Deps.Plugins.pluginYmlVersion
}

group = "dev.gamemode"
version = Deps.projectVersion

repositories {
    mavenCentral()
}

dependencies {
    // Lombok
    compileOnly("org.projectlombok:lombok:${Deps.Dependencies.lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${Deps.Dependencies.lombokVersion}")

    // JetBrains
    compileOnly("org.jetbrains:annotations:${Deps.Dependencies.annotationsVersion}")

    // Checkstyle
    checkstyle("com.puppycrawl.tools:checkstyle:${Deps.Dependencies.checkstyleVersion}")

    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:${Deps.Dependencies.junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Paperweight
    paperweight.paperDevBundle(Deps.Dependencies.paperVersion)

    // Night-Config
    paperLibrary("com.electronwill.night-config:yaml:${Deps.Dependencies.nightConfigVersion}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

paper {
    main = "dev.gamemode.chatchannels.ChatchannelsPlugin"
    apiVersion = "1.20"
    loader = "dev.gamemode.chatchannels.PluginLibrariesLoader"
    generateLibrariesJson = true
}

tasks {
    test {
        useJUnitPlatform()
    }
    assemble {
        dependsOn("reobfJar")
    }
}