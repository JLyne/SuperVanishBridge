plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components.getByName("java"))
            pom {
                url = "https://github.com/JLyne/SuperVanishBridge"
                developers {
                    developer {
                        id = "jim"
                        name = "James Lyne"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/JLyne/SuperVanishBridge.git"
                    developerConnection = "scm:git:ssh://github.com/JLyne/SuperVanishBridge.git"
                    url = "https://github.com/JLyne/SuperVanishBridge"
                }
            }
        }
    }
    repositories {
        maven {
            name = "notnull"
            credentials(PasswordCredentials::class)
            val releasesRepoUrl = uri("https://repo.not-null.co.uk/releases/") // gradle -Prelease publish
            val snapshotsRepoUrl = uri("https://repo.not-null.co.uk/snapshots/")
            url = if (project.hasProperty("release")) releasesRepoUrl else snapshotsRepoUrl
        }
    }
}