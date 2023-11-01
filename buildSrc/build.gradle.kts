plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("jacoco-reports") {
            id = "jacoco-reports"
            implementationClass = "com.riandinp.freegamesdb.plugins.JacocoReportPlugin"
        }
    }
}

dependencies {
    implementation("org.jacoco:org.jacoco.core:0.8.11")
}