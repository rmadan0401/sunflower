buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0") // ✅ Add this
    }
}

plugins {
    id("com.android.application")   // ✅ Correct placement
    id("com.github.triplet.play") version "3.12.1"
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.compose.compiler)
}

apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")
