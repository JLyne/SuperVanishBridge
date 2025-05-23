plugins {
    id("vanish-bridge.java-conventions")
}

dependencies {
    implementation(project(":VanishBridge-api"))

    compileOnly(libs.velocityApi)
    annotationProcessor(libs.velocityApi)
}
