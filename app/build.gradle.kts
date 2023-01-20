plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.HILT_PLUGIN)
}

android {
    namespace = "com.kenshi.calorietracker"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "com.kenshi.calorietracker.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packagingOptions {
        resources {
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
        }
        testOptions {
            packagingOptions {
                jniLibs {
                    useLegacyPackaging = true
                }
            }
        }
    }

    dependencies {

        implementation(Compose.compiler)
        implementation(Compose.ui)
        implementation(Compose.uiToolingPreview)
        implementation(Compose.hiltNavigationCompose)
        implementation(Compose.material)
        implementation(Compose.runtime)
        // implementation(Compose.navigation)
        implementation(Compose.composeDestinations)
        implementation(Compose.viewModelCompose)
        implementation(Compose.activityCompose)

        implementation(DaggerHilt.hiltAndroid)
        kapt(DaggerHilt.hiltCompiler)

        implementation(project(Modules.core))
        implementation(project(Modules.coreUi))
        implementation(project(Modules.onboardingPresentation))
        implementation(project(Modules.onboardingDomain))
        implementation(project(Modules.trackerPresentation))
        implementation(project(Modules.trackerDomain))
        implementation(project(Modules.trackerData))

        implementation(AndroidX.coreKtx)
        implementation(AndroidX.appCompat)

        implementation(Coil.coilCompose)

        implementation(Google.material)

        implementation(Retrofit.okHttp)
        implementation(Retrofit.retrofit)
        implementation(Retrofit.okHttpLoggingInterceptor)
        implementation(Retrofit.moshiConverter)

        kapt(Room.roomCompiler)
        implementation(Room.roomKtx)
        implementation(Room.roomRuntime)

        testImplementation(Testing.junit4)
        testImplementation(Testing.junitAndroidExt)
        testImplementation(Testing.truth)
        testImplementation(Testing.coroutines)
        testImplementation(Testing.turbine)
        testImplementation(Testing.composeUiTest)
        testImplementation(Testing.mockk)
        testImplementation(Testing.mockWebServer)

        androidTestImplementation(Testing.junit4)
        androidTestImplementation(Testing.junitAndroidExt)
        androidTestImplementation(Testing.truth)
        androidTestImplementation(Testing.coroutines)
        androidTestImplementation(Testing.turbine)
        androidTestImplementation(Testing.composeUiTest)
        androidTestImplementation(Testing.mockk)
        androidTestImplementation(Testing.mockkAndroid)
        androidTestImplementation(Testing.mockWebServer)
        androidTestImplementation(Testing.hiltTesting)
        kaptAndroidTest(DaggerHilt.hiltCompiler)
        androidTestImplementation(Testing.testRunner)
    }
}