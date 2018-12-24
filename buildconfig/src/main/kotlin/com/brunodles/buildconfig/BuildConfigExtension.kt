package com.brunodles.buildconfig

open class BuildConfigExtension {

    internal val configFields = mutableMapOf<String, Field>()
    @JvmField
    var targetPackage: String = ""

    fun field(type: String, name: String, value: String) {
        val nameToUppercase = name.toUpperCase()
        configFields[nameToUppercase] = Field(type, nameToUppercase, value)
    }

    class Field(val type: String, val name: String, val value: String)
}
