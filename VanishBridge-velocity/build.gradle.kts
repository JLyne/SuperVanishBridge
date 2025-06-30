plugins {
    id("vanish-bridge.java-conventions")
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":VanishBridge-api"))

    compileOnly(libs.velocityApi)
    annotationProcessor(libs.velocityApi)
}

tasks {
    shadowJar {
        archiveClassifier = ""
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        expand("version" to project.version)
    }
}