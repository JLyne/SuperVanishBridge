import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    java
}

group = "uk.co.notnull"
version = "2.0-SNAPSHOT"

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

repositories {
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://repo.not-null.co.uk/releases/")
    }

    maven {
        url = uri("https://repo.not-null.co.uk/snapshots/")
    }

    maven {
        url = uri("https://jitpack.io/")
    }

    mavenLocal()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks {
    compileJava {
        options.compilerArgs.addAll(listOf("-Xlint:all", "-Xlint:-processing"))
        options.encoding = "UTF-8"
    }
}
