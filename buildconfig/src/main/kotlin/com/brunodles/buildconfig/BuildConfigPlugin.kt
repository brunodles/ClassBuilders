package com.brunodles.buildconfig

import com.brunodles.auto.gradleplugin.AutoPlugin
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import java.io.File

@AutoPlugin("buildconfig", "com.brunodles.buildconfig")
open class BuildConfigPlugin : Plugin<Project> {

    companion object {
        @Suppress("unused")
        const val VERSION = "0.1"

        const val EXTENSION_NAME = "buildconfig"
        const val TASK_NAME = "generateBuildConfigClass"
        const val CLASS_NAME = "BuildConfig"
        const val GENERATED_PATH = "generated/source/javaBuildConfig"

        fun outputDir(project: Project): File =
                File(project.buildDir, GENERATED_PATH)
    }

    override fun apply(project: Project) {
        registerSourceSet(project)
        try {
            createTask(project)
            createExtension(project)
        } catch (e: Exception) {
            throw GradleException("Failed to find java or koltin plugin in this project", e)
        }
    }

    private fun registerSourceSet(project: Project) {
        project.convention.findPlugin(JavaPluginConvention::class.java)?.let { convention ->
            val mainSourceSet = convention.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
            mainSourceSet.java {
                it.srcDirs.add(outputDir(project))
            }
        }
    }

    private fun createTask(project: Project) {
        val tasks = project.tasks
        val buildConfigTask = tasks.create(TASK_NAME, BuildConfigTask::class.java)
        val compileKotlin = tasks.findByName("compileKotlin")
        if (compileKotlin == null)
            tasks.getByPath("compileJava").dependsOn.add(buildConfigTask)
        else
            compileKotlin.dependsOn.add(buildConfigTask)
    }

    private fun createExtension(project: Project) {
        project.extensions.create(EXTENSION_NAME, BuildConfigExtension::class.java)
    }

}