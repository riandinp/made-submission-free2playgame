package com.riandinp.freegamesdb

import org.gradle.kotlin.dsl.DependencyHandlerScope

object Dependencies {
    object AndroidX {
        const val CORE = "androidx.core:core-ktx:${Versions.AndroidX.CORE}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.AndroidX.APPCOMPAT}"
        const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.CONSTRAINTLAYOUT}"
        const val COORDINATORLAYOUT = "androidx.coordinatorlayout:coordinatorlayout:${Versions.AndroidX.COORDINATORLAYOUT}"
        const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.LIFECYCLE}"
        const val MULTIDEX = "androidx.multidex:multidex:${Versions.AndroidX.MULTIDEX}"
        const val ARCH_CORE_TESTING = "androidx.arch.core:core-testing:${Versions.AndroidX.ARCH_CORE}"
    }

    object KotlinX {
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KotlinX.COROUTINES}"
        const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KotlinX.COROUTINES}"
        const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KotlinX.COROUTINES}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Versions.Google.MATERIAL}"
    }

    object Test {
        object Unit {
            const val JUNIT = "junit:junit:${Versions.Test.JUNIT}"
            const val MOCKITO_CORE = "org.mockito:mockito-core:${Versions.Test.MOCKITO}"
            const val MOCKITO_INLINE = "org.mockito:mockito-inline:${Versions.Test.MOCKITO}"
        }

        object Integration {
            const val JUNIT = "androidx.test.ext:junit:${Versions.Test.JUNIT_INTEGRATION}"
            const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.Test.ESPRESSO_CORE}"
        }
    }

    object DI {
        const val KOIN_ANDROID = "io.insert-koin:koin-android:${Versions.DI.KOIN_ANDROID}"
    }

    object Room {
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    }

    object Retrofit {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    }

    object OKHTTP3 {
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR}"
    }

    const val LIKE_BUTTON = "com.github.jd-alexander:LikeButton:${Versions.LIKE_BUTTON}"
    const val SQL_CIPHER = "net.zetetic:android-database-sqlcipher:${Versions.SQL_CIPHER}"
    const val SQL_LITE = "androidx.sqlite:sqlite:${Versions.SQL_LITE}"

    //shared depedencies
    private const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    private const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:${Versions.LEAK_CANARY}"

    fun DependencyHandlerScope.sharedDependencies() {
        "implementation"(AndroidX.CORE)
        "implementation"(AndroidX.APPCOMPAT)
        "implementation"(AndroidX.CONSTRAINTLAYOUT)
        "implementation"(AndroidX.MULTIDEX)
        "implementation"(Google.MATERIAL)
        "implementation"(DI.KOIN_ANDROID)
        "implementation"(GLIDE)

        "implementation"(KotlinX.COROUTINES_CORE)
        "implementation"(KotlinX.COROUTINES_ANDROID)
        "testImplementation"(KotlinX.COROUTINES_TEST)

        "testImplementation"(Test.Unit.JUNIT)
        "testImplementation"(Test.Unit.MOCKITO_CORE)
        "testImplementation"(Test.Unit.MOCKITO_INLINE)
        "testImplementation"(AndroidX.ARCH_CORE_TESTING)

        "androidTestImplementation"(Test.Integration.JUNIT)
        "androidTestImplementation"(Test.Integration.ESPRESSO_CORE)
        "debugImplementation"(LEAK_CANARY)
    }
}