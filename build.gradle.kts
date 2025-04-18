// Root build.gradle.kts
plugins {
    id("com.android.application") version "8.9.1" apply false
    id("com.android.library") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

allprojects {

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}