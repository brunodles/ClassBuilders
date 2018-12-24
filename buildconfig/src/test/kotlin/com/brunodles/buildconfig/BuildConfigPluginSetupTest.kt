package com.brunodles.buildconfig

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

    private lateinit var project: Project

    @Before
    fun setupProject() {
        project = ProjectBuilder.builder().build()
        project.apply {
            it.plugin(JavaPlugin::class.java)
            it.plugin(BuildConfigPlugin::class.java)
        }
    }

    @Test
    fun withoutSetup_shouldReturnEmptyTargetPackage() {
        val extension = project.extensions.getByName(BuildConfigPlugin.EXTENSION_NAME) as BuildConfigExtension
        assertTrue(extension.targetPackage.isEmpty())
    }

    @Test
    fun shouldAddBuildConfigExtension() {
        assertTrue(project.extensions.getByName(BuildConfigPlugin.EXTENSION_NAME) is BuildConfigExtension)
    }

    @Test
    fun shuoldAddBuildConfigTask() {
        assertTrue(project.tasks.getByName(BuildConfigPlugin.TASK_NAME) is BuildConfigTask)
    }
}
