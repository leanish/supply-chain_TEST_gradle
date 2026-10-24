buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.20") {
            because("GHSA-r937-wjx7-w2jp: the Kotlin DSL plugin brings a vulnerable Kotlin Gradle plugin")
        }
    }
}

plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "6.7.13"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
