package com.brunodles.buildconfig

import com.brunodles.classbuilder.CoreBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class BuildConfigTask : DefaultTask() {

    init {
        group = "buildConfig"
        description = "Generate BuildConfig class."
        outputs.upToDateWhen { false }
        finalizedBy("assemble")
    }

    @Suppress("unused")
    @TaskAction
    fun createBuildConfig() {
        val output = BuildConfigPlugin.outputDir(project)
        val extension: BuildConfigExtension = project.extensions
                .getByName(BuildConfigPlugin.EXTENSION_NAME) as BuildConfigExtension

        val coreBuilder = CoreBuilder(BuildConfigPlugin.CLASS_NAME, extension.targetPackage)

        extension.configFields.forEach { _, field ->
            coreBuilder.field(field.type, field.name, field.value)
        }

        coreBuilder.write(output)
    }
}
