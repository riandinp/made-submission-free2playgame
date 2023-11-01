package com.riandinp.freegamesdb.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoReportsContainer

class JacocoReportPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run { apply("jacoco") }

            createTask()

            dependencies { "implementation"("org.jacoco:org.jacoco.core:0.8.11") }
        }

    }

    private fun Project.createTask() {
        tasks.withType<Test> {
            configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
                excludes = mutableListOf("jdk.internal.*")
            }
        }

        tasks.register<JacocoReport>("jacocoAndroidTestReport") {
            description = "Code coverage report for Unit tests."
            group = "Reporting"
            dependsOn("testDebugUnitTest", "createDebugUnitTestCoverageReport")

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

            val classDirectoriesTree = fileTree(buildDir) {
                include(
                    "**/classes/**/main/**",
                    "**/intermediates/classes/debug/**",
                    "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
                    "**/tmp/kotlin-classes/debug/**"
                )

                exclude(fileFilter)
            }

            val sourceDirectoriesTree = fileTree("${project.buildDir}") {
                include(
                    "src/main/java/**",
                    "src/main/kotlin/**",
                    "src/debug/java/**",
                    "src/debug/kotlin/**"
                )
            }

            val executionDataTree = fileTree(buildDir) {
                include(
                    "outputs/code_coverage/**/*.ec",
                    "jacoco/jacocoTestReportDebug.exec",
                    "jacoco/testDebugUnitTest.exec",
                    "jacoco/test.exec"
                )
            }

            reports {
                xml.required.set(true)
                html.required.set(true)
                xml.outputLocation.set(file("${buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"))
                html.outputLocation.set(file("${buildDir}/reports/jacoco/jacocoTestReport/html"))
            }
            sourceDirectories.setFrom(sourceDirectoriesTree)
            classDirectories.setFrom(classDirectoriesTree)
            executionData.setFrom(executionDataTree)
        }
    }
}