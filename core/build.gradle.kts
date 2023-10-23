import com.riandinp.freegamesdb.Dependencies
import com.riandinp.freegamesdb.Dependencies.sharedDependencies
import com.riandinp.freegamesdb.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.riandinp.freegamesdb.core"
    compileSdk = Versions.App.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.App.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    implementation(Dependencies.KotlinX.COROUTINES_CORE)
    implementation(Dependencies.KotlinX.COROUTINES_ANDROID)
    api(Dependencies.AndroidX.LIFECYCLE_LIVEDATA_KTX)
}