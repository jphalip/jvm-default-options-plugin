plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.16.0"
    id("com.diffplug.spotless") version "6.15.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.google.guava:guava:31.1-jre")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

spotless {
    java {
        removeUnusedImports()
        googleJavaFormat("1.8").aosp().reflowLongStrings().groupArtifact("com.google.googlejavaformat:google-java-format")
    }
}

intellij {
    version.set("2022.1.4")
    type.set("IC")
    plugins.set(listOf("java"))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
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