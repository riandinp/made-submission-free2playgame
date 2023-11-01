import com.riandinp.freegamesdb.Dependencies
import com.riandinp.freegamesdb.Dependencies.sharedDependencies
import com.riandinp.freegamesdb.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("jacoco-reports")
}

jacoco {
    toolVersion = "0.8.11"
}

android {
    namespace = "com.riandinp.freegamesdb.core"
    compileSdk = Versions.App.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.App.MIN_SDK

        buildConfigField("String", "BASE_URL", "\"https://www.freetogame.com/api/\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            enableUnitTestCoverage = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    sharedDependencies()

    implementation(Dependencies.Room.ROOM_RUNTIME)
    implementation(Dependencies.Room.ROOM_KTX)
    annotationProcessor(Dependencies.Room.ROOM_COMPILER)
    ksp(Dependencies.Room.ROOM_COMPILER)

    implementation(Dependencies.Retrofit.RETROFIT)
    implementation(Dependencies.Retrofit.CONVERTER_GSON)
    implementation(Dependencies.OKHTTP3.LOGGING_INTERCEPTOR)

    api(Dependencies.AndroidX.LIFECYCLE_LIVEDATA_KTX)

    implementation(Dependencies.SQL_CIPHER)
    implementation(Dependencies.SQL_LITE)
}