package com.brunodles.buildconfig

import com.brunodles.auto.gradleplugin.AutoPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import java.io.File

@AutoPlugin("buildconfig")
class BuildConfigPlugin : Plugin<Project> {

    companion object {
        @Suppress("unused")
        const val VERSION = "0.1"

        const val EXTENSION_NAME = "buildconfig"
        const val TASK_NAME = "generateBuildConfigClass"
        const val CLASS_NAME = "BuildConfig"
        private const val GENERATED_PATH = "generated/source/javaBuildConfig"

        fun outputDir(project: Project): File =
                File(project.buildDir, GENERATED_PATH)
    }

    override fun apply(project: Project) {
        registerSourceSet(project)
        createTask(project)
        createExtension(project)
    }

    private fun registerSourceSet(project: Project) {
        val javaPluginConvention = project.convention.getPlugin(JavaPluginConvention::class.java)
        val mainSourceSet = javaPluginConvention.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
        mainSourceSet.java {
            it.srcDirs.add(outputDir(project))
        }
    }

    private fun createTask(project: Project) {
        val buildConfigTask = project.tasks.create(TASK_NAME, BuildConfigTask::class.java)
        val compileKotlin = project.tasks.findByName("compileKotlin")
        if (compileKotlin != null)
            compileKotlin.dependsOn.add(buildConfigTask)
        else
            project.tasks.getByName("compileJava").dependsOn.add(buildConfigTask)
    }

    private fun createExtension(project: Project) {
        project.extensions.create(EXTENSION_NAME, BuildConfigExtension::class.java)
    }

}