rootProject.name = "VanishBridge"

include(":VanishBridge-api")
include(":VanishBridge-helper")
include(":VanishBridge-paper")
include(":VanishBridge-velocity")
project(":VanishBridge-api").projectDir = file("VanishBridge-api")
project(":VanishBridge-helper").projectDir = file("VanishBridge-helper")
project(":VanishBridge-paper").projectDir = file("VanishBridge-paper")
project(":VanishBridge-velocity").projectDir = file("VanishBridge-velocity")