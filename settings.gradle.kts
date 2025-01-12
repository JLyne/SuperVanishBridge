rootProject.name = "SuperVanishBridge"

include(":SuperVanishBridge-api")
include(":SuperVanishBridge-helper")
include(":SuperVanishBridge-paper")
include(":SuperVanishBridge-velocity")
project(":SuperVanishBridge-api").projectDir = file("SuperVanishBridge-api")
project(":SuperVanishBridge-helper").projectDir = file("SuperVanishBridge-helper")
project(":SuperVanishBridge-paper").projectDir = file("SuperVanishBridge-paper")
project(":SuperVanishBridge-velocity").projectDir = file("SuperVanishBridge-velocity")