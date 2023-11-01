import com.riandinp.freegamesdb.Dependencies.sharedDependencies
import com.riandinp.freegamesdb.Versions

plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("jacoco-reports")
}

jacoco {
    toolVersion = "0.8.11"
}

android {
    namespace = "com.riandinp.freegamesdb.favorite"
    compileSdk = Versions.App.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.App.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
        }

        release {
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))

    sharedDependencies()
}