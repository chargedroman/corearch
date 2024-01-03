plugins {
    id("kotlin-android")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    compileSdk = 34
    namespace = "com.r.immoscoutpuller"

    defaultConfig {
        applicationId = "com.r.quicktrade"
        minSdk = 26
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "(pp)"
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            applicationIdSuffix = ""
            versionNameSuffix = ""

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    android {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(18))
            }
        }
        kotlinOptions {
            jvmTarget = "18"
        }
    }

    testOptions.unitTests.isReturnDefaultValues = true
    lint.disable += "ContentDescription"
}

dependencies {
    implementation(project(":basearch"))

    testImplementation("org.koin:koin-test:2.0.1")
    testImplementation("junit:junit:4.13.2")
}