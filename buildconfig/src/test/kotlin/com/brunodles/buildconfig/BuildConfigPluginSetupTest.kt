package com.brunodles.buildconfig

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BuildConfigPluginSetupTest {

    @Test(expected = GradleException::class)
    fun withoutJavaPlugin_shouldThrowException() {
        val project: Project = ProjectBuilder.builder().build()
        project.apply {
            it.plugin(BuildConfigPlugin::class.java)
        }
    }

}
