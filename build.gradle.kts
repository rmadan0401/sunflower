// Root build.gradle.kts

plugins {
    // Removed: alias(libs.plugins.android.application) ❌
    id("com.github.triplet.play") version "3.12.1" // ✅ Custom plugin directly declared
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.compose.compiler)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // No buildscript dependencies needed here right now
    }
}

// If this file exists, keep it. It's likely configuring version catalog update rules.
apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")
