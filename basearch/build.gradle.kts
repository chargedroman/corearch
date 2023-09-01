
plugins {
    id("kotlin-android")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    compileSdk = 33
    namespace = "com.roman.basearch"

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
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
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0")
    api("androidx.appcompat:appcompat:1.6.1")
    api("androidx.core:core-ktx:1.10.1")

    // Base
    api("androidx.recyclerview:recyclerview:1.3.0")
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    api("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    api("com.google.android.material:material:1.9.0")

    // DI
    api("org.koin:koin-androidx-scope:2.0.1")
    api("org.koin:koin-androidx-viewmodel:2.0.1")
    api("org.koin:koin-androidx-ext:2.0.1")

    // Jetpack navigation
    api("androidx.navigation:navigation-fragment-ktx:2.6.0")
    api("androidx.navigation:navigation-ui-ktx:2.6.0")
    api("androidx.fragment:fragment-ktx:1.6.0")

    // Simplifying network operations
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.6.0")
    api("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Image caching & loading
    api("com.github.bumptech.glide:glide:4.12.0")

    // time formatting
    api("net.danlew:android.joda:2.10.6")
    api("org.apache.commons:commons-text:1.7")

    // key value store
    api("com.tencent:mmkv-static:1.2.10")

    // work manager
    api("androidx.work:work-runtime-ktx:2.8.1")

    // browser
    api("androidx.browser:browser:1.4.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.koin:koin-test:2.0.1")
    testImplementation("android.arch.core:core-testing:1.1.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("com.google.truth:truth:1.1.3")
}
