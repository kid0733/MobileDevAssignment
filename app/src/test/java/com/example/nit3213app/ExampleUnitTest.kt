package com.example.nit3213app

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * This now contains a basic test to verify the project package structure.
 */
class ExampleUnitTest {
    
    @Test
    fun `project package should be correct`() {
        val packageName = "com.example.nit3213app"
        assertEquals("Package name should match", packageName, this::class.java.packageName)
    }
    
    @Test
    fun `basic string operations should work`() {
        val testString = "NIT3213"
        assertEquals("String length should be 7", 7, testString.length)
        assertTrue("Should contain NIT", testString.contains("NIT"))
        assertTrue("Should contain 3213", testString.contains("3213"))
    }
}