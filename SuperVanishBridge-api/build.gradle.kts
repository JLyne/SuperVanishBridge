plugins {
    id("super-vanish-bridge.java-conventions")
    id("super-vanish-bridge.publishing-conventions")
}

dependencies {
    compileOnly(libs.velocityApi)
    annotationProcessor(libs.velocityApi)
}
