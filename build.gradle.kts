plugins {
    id("java")
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.intellij") version "1.17.4"
    id("com.diffplug.spotless") version "6.25.0"
    id("pmd")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.google.guava:guava:33.3.1-jre")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.1")
}

spotless {
    java {
        removeUnusedImports()
        googleJavaFormat("1.22.0")
            .aosp()
            .reflowLongStrings()
            .groupArtifact("com.google.googlejavaformat:google-java-format")
    }
}

pmd {
    isConsoleOutput = true
    toolVersion = "6.46.0"
    rulesMinimumPriority.set(5)
    ruleSetFiles = rootProject.files("pmd-config.xml")
    ruleSets = emptyList()
    isIgnoreFailures = false
}

tasks.named<Pmd>("pmdMain") {
    reports {
        html.required.set(true)
    }
}

intellij {
    version.set("2024.1.2")
    type.set("IC")
    plugins.set(listOf("java"))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

}