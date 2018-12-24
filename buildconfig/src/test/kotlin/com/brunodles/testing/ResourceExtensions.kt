package com.brunodles.testing

fun String.readResource(): String {
    return ClassLoader.getSystemClassLoader()
            .getResource(this)
            .readText()
}