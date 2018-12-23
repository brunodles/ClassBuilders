package com.brunodles.classbuilder

fun String.loadResource(): String {
    return ClassLoader.getSystemClassLoader()
            .getResource(this)
            .readText()
}