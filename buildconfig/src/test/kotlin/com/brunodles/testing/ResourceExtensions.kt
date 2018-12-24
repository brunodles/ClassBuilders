package com.brunodles.testing

fun String.loadResource(): String {
    return ClassLoader.getSystemClassLoader()
            .getResource(this)
            .readText()
}