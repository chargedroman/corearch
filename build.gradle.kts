// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven {url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath ("com.android.tools.build:bundletool:0.14.0")
        classpath ("com.android.tools.build:gradle:8.0.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath ("com.google.gms:google-services:4.3.14")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url =uri("https://jitpack.io")}
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}