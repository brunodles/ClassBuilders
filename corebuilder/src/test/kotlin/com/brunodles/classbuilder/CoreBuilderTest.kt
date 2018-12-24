package com.brunodles.classbuilder

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File

@RunWith(JUnit4::class)
class CoreBuilderTest {

    @Rule
    @JvmField
    val temporaryFolder = TemporaryFolder()

    @Test
    fun whenBuild_withClassNameOnly_shouldReturnEmptyClass() {
        val result: String = CoreBuilder("EmptyClass")
                .build()

        assertThat(result, `is`("class_empty".loadResource()))
    }

    @Test
    fun whenBuild_withClassName_andBlankPackageName_shouldReturnEmptyClass() {
        val result: String = CoreBuilder("EmptyClass", "")
                .build()

        assertThat(result, `is`("class_empty".loadResource()))
    }

    @Test
    fun whenBuild_withClassName_andPackage_shouldReturnEmptyClassWithPackage() {
        val result: String = CoreBuilder("ClassWithPackage", "packagename")
                .build()

        assertThat(result, `is`("class_with_package".loadResource()))
    }

    @Test
    fun whenBuild_withSingleField_shouldReturnClassWithField() {
        val result: String = CoreBuilder("SingleField") {
            field("String", "NAME", "\"Bruce Wayne\"")
        }.build()

        assertThat(result, `is`("class_single_field".loadResource()))
    }

    @Test
    fun whenBuild_withSameField_shouldOverridePreviousFieldValue() {
        val result: String = CoreBuilder("SingleField") {
            field("String", "NAME", "\"Peter Parker\"")
            field("String", "NAME", "\"Bruce Wayne\"")
        }.build()

        assertThat(result, `is`("class_single_field".loadResource()))
    }

    @Test
    fun whenBuild_withSameFieldAndNestedClass_shouldOverridePreviousFieldValue() {
        val result: String = CoreBuilder("RootClass") {
            field("String", "Nested", "\"Peter Parker\"")
            nested("Nested")
        }.build()

        assertThat(result, `is`("class_nested".loadResource()))
    }

    @Test
    fun whenBuild_withNestedClass_shouldReturnClassWithNestedClass() {
        val result: String = CoreBuilder("RootClass") {
            nested("Nested")
        }.build()

        assertThat(result, `is`("class_nested".loadResource()))
    }

    @Test
    fun whenBuild_withNestedClass_withField_shouldReturnClassWithNestedClassWithField() {
        val result: String = CoreBuilder("RootClass") {
            nested("Nested") {
                field("String", "NAME", "\"Peter Parker\"")
            }
        }.build()

        assertThat(result, `is`("class_nested_field".loadResource()))
    }

    @Test
    fun whenBuild_with3NestedClasses_shouldReturn3NestedClasses() {
        val result: String = CoreBuilder("RootClass") {
            nested("Level1") {
                nested("Level2") {
                    nested("Level3") {
                    }
                }
            }
        }.build()

        assertThat(result, `is`("class_nested_3levels".loadResource()))
    }

    @Test
    fun whenWrite_withClassNameOnly_shouldCreateFileOnRootFolder() {
        CoreBuilder("EmptyClass")
                .write(temporaryFolder.root)

        val file = File(temporaryFolder.root, "EmptyClass.java")
        assertTrue(file.exists())
        assertEquals(file.readText(), "class_empty".loadResource())
    }

    @Test
    fun whenWrite_withClassName_andPackage_shouldCreatePackageTree() {
        CoreBuilder("DeepClass", packageName = "com.brunodles.classbuilders.corebuilder")
                .write(temporaryFolder.root)

        val packageFile = File(temporaryFolder.root, "com/brunodles/classbuilders/corebuilder")
        val file = File(packageFile, "DeepClass.java")
        assertTrue("File must exists", file.exists())
        assertEquals(file.readText(), "class_deep".loadResource())
    }
}
