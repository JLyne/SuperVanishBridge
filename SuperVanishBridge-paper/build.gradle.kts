import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("super-vanish-bridge.java-conventions")
    alias(libs.plugins.pluginYmlPaper)
}

dependencies {
    compileOnly(libs.paperApi)
    compileOnly(libs.superVanish)
}

paper {
    name = rootProject.name
    main = "uk.co.notnull.supervanishbridge.SuperVanishBridge"
    apiVersion = libs.versions.paperApi.get().replace(Regex("\\-R\\d.\\d-SNAPSHOT"), "")
    authors = listOf("Jim (AnEnragedPigeon)")
    description = "Bridge plugin to notify Velocity of a player's SuperVanish state on a backend server"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP

    serverDependencies {
        register("SuperVanish") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}
