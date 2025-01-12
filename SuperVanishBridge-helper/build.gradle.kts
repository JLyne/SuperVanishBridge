plugins {
    id("super-vanish-bridge.java-conventions")
    id("super-vanish-bridge.publishing-conventions")
}

dependencies {
    compileOnly(project(":SuperVanishBridge-api"))
    compileOnly(libs.velocityApi)
    compileOnly(libs.cloudVelocity)
    compileOnly(libs.cloudMinecraftExtras)
    compileOnly(libs.cloudAnnotations)

    annotationProcessor(libs.velocityApi)
}
