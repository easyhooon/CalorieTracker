// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.AGP apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.AGP apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    // id(Plugins.KOTLIN_JVM) version Versions.KOTLIN_JVM apply false
    id(Plugins.DAGGER_HILT) version Versions.HILT apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}