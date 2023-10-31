package plugins

import org.gradle.api.tasks.testing.Test

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = mutableListOf("jdk.internal.*")
    }
}

val fileFilter = mutableSetOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
    "**/*\$inlined$*.*", // Kotlin specific, Jacoco can not handle several "$" in class name.
    "**/*Binding.*",
    "**/*App.*",
    "**/*Application.*",
    "**/*Activity.*",
    "**/*Fragment.*",
    "**/*Adapter.*",
    "**/di/**",
)
private val classDirectoriesTree = fileTree(buildDir) {
    include(
        "**/classes/**/main/**",
        "**/intermediates/classes/debug/**",
        "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
        "**/tmp/kotlin-classes/debug/**"
    )

    exclude(fileFilter)
}

private val sourceDirectoriesTree = fileTree("${project.projectDir}") {
    include(
        "src/main/java/**",
        "src/main/kotlin/**",
        "src/debug/java/**",
        "src/debug/kotlin/**"
    )
}

private val executionDataTree = fileTree(buildDir) {
    include(
        "outputs/code_coverage/**/*.ec",
        "jacoco/jacocoTestReportDebug.exec",
        "jacoco/testDebugUnitTest.exec",
        "jacoco/test.exec"
    )
}

fun JacocoReportsContainer.reports() {
    xml.required.set(true)
    html.required.set(true)
    xml.outputLocation.set(file("${buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"))
    html.outputLocation.set(file("${buildDir}/reports/jacoco/jacocoTestReport/html"))
}

fun JacocoCoverageVerification.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

fun JacocoReport.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}


tasks.register<JacocoReport>("jacocoAndroidTestReport") {
    description = "Code coverage report for Unit tests."
    group = "Reporting"
    dependsOn("testDebugUnitTest", "createDebugUnitTestCoverageReport")
    reports {
        reports()
    }
    setDirectories()
}

tasks.register<JacocoCoverageVerification>("jacocoAndroidCoverageVerification") {
    description = "Code coverage verification for Unit tests."
    group = "Verification"
    dependsOn("testDebugUnitTest", "createDebugUnitTestCoverageReport")
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTIONAL"
                value = "COVEREDRATIO"
                minimum = "0.1".toBigDecimal()
            }
        }
    }
    setDirectories()
}

fun Project.setupJacoco(name: String, description: String = "Generate Jacoco coverage reports after running tests.") {
    this.tasks.register<JacocoReport>(name) {
        this.description = description
        group = "Reporting"
        reports {
            reports()
        }
        setDirectories()
    }
}