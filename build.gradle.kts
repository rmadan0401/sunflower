// Root build.gradle.kts

plugins {
    alias(libs.plugins.android.application)         // ✅ FROM version catalog
    id("com.github.triplet.play") version "3.12.1"   // ✅ Manually declared with version
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
        
        
    }
}

apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")
