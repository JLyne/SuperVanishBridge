plugins {
    id("super-vanish-bridge.java-conventions")
}

dependencies {
    implementation(project(":SuperVanishBridge-api"))

    compileOnly(libs.velocityApi)
    annotationProcessor(libs.velocityApi)
}
