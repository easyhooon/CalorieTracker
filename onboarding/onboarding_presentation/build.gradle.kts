apply {
    from("$rootDir/compose-module.gradle")
}

plugins {
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"
}

ksp {
    arg("compose-destinations.mode", "navgraphs")
    arg("compose-destinations.moduleName", "onboarding")
}


dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.onboardingDomain))

    "ksp"(Compose.composeDestinationsKsp)
}