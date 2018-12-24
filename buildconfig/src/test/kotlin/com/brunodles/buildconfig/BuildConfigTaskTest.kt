package com.brunodles.buildconfig

import com.brunodles.testing.GradleRule
import com.brunodles.testing.readResource
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BuildConfigTaskTest {

    @Rule
    @JvmField
    val gradleRule = GradleRule()

    @Test
    fun withoutSetup_shouldCreateEmptyClass() {
        gradleRule.setup {
            withBuildGradle("empty_gradle")
            withArguments("compileJava", "--stacktrace")
        }

        val result = gradleRule.file("build/${BuildConfigPlugin.GENERATED_PATH}/${BuildConfigPlugin.CLASS_NAME}.java")
                .readText()
        assertEquals("empty_class".readResource(), result)
    }

    @Test
    fun withJavaPlugin_andTargetPackageOnly_shouldCreateClassInPackage() {
        gradleRule.setup {
            withBuildGradle("package_gradle")
            withArguments("compileJava", "--stacktrace")
        }

        val result = gradleRule.file("build/${BuildConfigPlugin.GENERATED_PATH}/com/brunodles/${BuildConfigPlugin.CLASS_NAME}.java")
                .readText()
        assertEquals("package_class".readResource(), result)
    }

    @Test
    fun withKotlinPlugin_andTargetPackage_shouldCreateClassInPackage() {
        gradleRule.setup {
            withBuildGradle("package_gradle_kotlin")
            withArguments("compileKotlin", "--stacktrace")
        }

        val result = gradleRule.file("build/${BuildConfigPlugin.GENERATED_PATH}/com/brunodles/${BuildConfigPlugin.CLASS_NAME}.java")
                .readText()
        assertEquals("package_class".readResource(), result)
    }

    @Test
    fun withJavaPlugin_andTargetPackage_andSingleField_shouldCreateClass() {
        gradleRule.setup {
            withBuildGradle("single_gradle")
            withArguments("compileJava", "--stacktrace")
        }

        val result = gradleRule.file("build/${BuildConfigPlugin.GENERATED_PATH}/com/brunodles/${BuildConfigPlugin.CLASS_NAME}.java")
                .readText()
        assertEquals("single_class".readResource(), result)
    }
}