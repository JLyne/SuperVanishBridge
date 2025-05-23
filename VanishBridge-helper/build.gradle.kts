plugins {
    id("vanish-bridge.java-conventions")
    id("vanish-bridge.publishing-conventions")
}

dependencies {
    compileOnly(project(":VanishBridge-api"))
    compileOnly(libs.velocityApi)
    compileOnly(libs.cloudVelocity)
    compileOnly(libs.cloudMinecraftExtras)
    compileOnly(libs.cloudAnnotations)

    annotationProcessor(libs.velocityApi)
}
