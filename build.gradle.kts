// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:2.7.7")
    }
}

plugins {
    // This plugin is used to create parcelable classes in easy and fast way
    // id("kotlin-parcelize")
    // This plugin is used to create safe args for navigation
    // id("androidx.navigation.safeargs")
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}