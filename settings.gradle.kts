// settings.gradle.kts
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
    dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Or PREFER_SETTINGS if needed
    repositories {
        google()       // Required
        mavenCentral() // Required
    }
}
rootProject.name = "BlockPy" // Your project name
include(":app")


