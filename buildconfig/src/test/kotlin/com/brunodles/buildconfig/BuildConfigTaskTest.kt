package com.brunodles.buildconfig

import com.brunodles.testing.loadResource
import com.brunodles.testing.withJacoco
import org.gradle.testkit.runner.GradleRunner
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class BuildConfigTaskTest {

    @Rule
    @JvmField
    val testProjectDir = TemporaryFolder()

    private fun file(path: String? = null): File {
        return if (path == null)
            testProjectDir.root
        else
            File(file(), path)
    }

    @Test
    fun withJavaPlugin_andTargetPackageOnly_shouldCreateEmptyClass() {
        file("build.gradle")
                .writeText("empty_gradle".loadResource())
        GradleRunner.create()
                .withProjectDir(file())
                .withArguments("compileJava", "--stacktrace")
                .withPluginClasspath()
                .withDebug(true)
                .withJacoco()
                .build()

        val result = file("build/${BuildConfigPlugin.GENERATED_PATH}/com/brunodles/${BuildConfigPlugin.CLASS_NAME}.java")
                .readText()
        assertEquals("empty_class".loadResource(), result)
    }

    @Test
    fun withKotlinPlugin_andTargetPackage_shouldCreateEmptyClass() {
        file("build.gradle")
                .writeText("empty_gradle_kotlin".loadResource())
        GradleRunner.create()
                .withProjectDir(file())
                .withArguments("compileKotlin", "--stacktrace")
                .withPluginClasspath()
                .withDebug(true)
                .withJacoco()
                .build()

        val result = file("build/${BuildConfigPlugin.GENERATED_PATH}/com/brunodles/${BuildConfigPlugin.CLASS_NAME}.java")
                .readText()
        assertEquals("empty_class".loadResource(), result)
    }

    @Test
    fun withJavaPlugin_andTargetPackage_andSingleField_shouldCreateClass() {
        file("build.gradle")
                .writeText("single_gradle".loadResource())
        GradleRunner.create()
                .withProjectDir(file())
                .withArguments("compileJava", "--stacktrace")
                .withPluginClasspath()
                .withDebug(true)
                .withJacoco()
                .build()

        val result = file("build/${BuildConfigPlugin.GENERATED_PATH}/com/brunodles/${BuildConfigPlugin.CLASS_NAME}.java")
                .readText()
        assertEquals("single_class".loadResource(), result)
    }
}