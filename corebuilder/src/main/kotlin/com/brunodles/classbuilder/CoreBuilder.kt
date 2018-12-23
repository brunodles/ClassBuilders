package com.brunodles.classbuilder

import java.io.File
import java.lang.StringBuilder

class CoreBuilder(
        private val className: String,
        private val packageName: String? = null,
        private val modifiers: String? = null,
        func: (CoreBuilder.() -> Unit)? = null
) {

    private val map = mutableMapOf<String, String>()

    init {
        func?.invoke(this)
    }

    fun build(): String {
        val builder = StringBuilder()
        if (packageName != null)
            builder.append("package $packageName;\n")
        if (modifiers == null)
            builder.append("public class $className {\n")
        else
            builder.append("public $modifiers class $className {\n")
        map.forEach { key, value ->
            value.lines().forEach {
                builder.append("    ")
                        .append(it)
                        .append("\n")
            }
        }
        builder.append("}")
        return builder.toString()
    }

    fun field(type: String, name: String, value: String): CoreBuilder {
        map[name] = "public static final $type $name = $value;"
        return this
    }

    fun nested(name: String, func: (CoreBuilder.() -> Unit)? = null) {
        val coreBuilder = CoreBuilder(name, modifiers = "static final")
        func?.invoke(coreBuilder)
        map[name] = coreBuilder.build()
    }

    fun write(rootDir: File) {
        val classFile = if (packageName == null) {
            File(rootDir, "$className.java")
        } else {
            val packageFile = File(rootDir, packageName.replace(Regex("\\."), "/"))
            packageFile.mkdirs()
            File(packageFile, "$className.java")
        }
        classFile.writeText(build())
    }
}
