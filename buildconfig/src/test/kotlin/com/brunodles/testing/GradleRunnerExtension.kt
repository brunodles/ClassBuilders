package com.brunodles.testing

import org.gradle.testkit.runner.GradleRunner
import java.io.File
import java.io.InputStream

fun GradleRunner.withJacoco(): GradleRunner {
    ClassLoader.getSystemClassLoader()
            .getResourceAsStream("testkit-gradle.properties")
            .toFile(File(projectDir, "gradle.properties"))
    return this
}

private fun InputStream.toFile(file: File) {
    use { input ->
        file.outputStream().use { input.copyTo(it) }
    }
}
