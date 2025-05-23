plugins {
    id("vanish-bridge.java-conventions")
    id("vanish-bridge.publishing-conventions")
}

dependencies {
    compileOnly(libs.velocityApi)
    annotationProcessor(libs.velocityApi)
}
