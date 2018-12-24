package com.brunodles.testing

import org.gradle.testkit.runner.GradleRunner
import org.junit.rules.TemporaryFolder
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.File

class GradleRule : TestRule {

    val testProjectDir = TemporaryFolder()
    private var arguments: List<String> = emptyList()

    override fun apply(base: Statement, description: Description): Statement {
        return testProjectDir.apply(object : Statement() {
            override fun evaluate() {
                base.evaluate()
            }

        }, description)
    }

    fun file(path: String? = null): File {
        return if (path == null)
            testProjectDir.root
        else
            File(file(), path)
    }

    fun setup(func: GradleRuleSetup.() -> Unit) {
        func(GradleRuleSetup(this))
        GradleRunner.create()
                .withProjectDir(file())
                .withArguments(arguments)
                .withPluginClasspath()
                .withDebug(true)
                .withJacoco()
                .build()
    }

    class GradleRuleSetup(private val gradleRule: GradleRule) {

        fun withBuildGradle(resourcePath: String) {
            gradleRule.file("build.gradle")
                    .writeText(resourcePath.readResource())
        }

        fun withArguments(vararg arguments: String) {
            gradleRule.arguments = arguments.toList()
        }
    }
}