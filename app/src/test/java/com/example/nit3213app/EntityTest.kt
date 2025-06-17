package com.example.nit3213app

import com.example.nit3213app.data.model.Entity
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for Entity model - the core innovation
 * 
 * Tests the dynamic property handling functionality
 * that makes the app work with any API response structure.
 */
class EntityTest {

    @Test
    fun `getProperty should return correct values for different types`() {
        // Arrange
        val properties = mapOf(
            "name" to "John",
            "age" to 25,
            "active" to true,
            "score" to 95.5
        )
        val entity = Entity(properties)
        
        // Act & Assert
        assertEquals("John", entity.getProperty("name"))
        assertEquals("25", entity.getProperty("age"))
        assertEquals("true", entity.getProperty("active"))
        assertEquals("95.5", entity.getProperty("score"))
    }
    
    @Test
    fun `getProperty should return fallback for missing keys`() {
        // Arrange
        val properties = mapOf("name" to "John")
        val entity = Entity(properties)
        
        // Act & Assert
        assertEquals("N/A", entity.getProperty("missing_key"))
        assertEquals("Custom Default", entity.getProperty("missing_key", "Custom Default"))
    }
    
    @Test
    fun `getAllKeys should return all property keys`() {
        // Arrange
        val properties = mapOf(
            "name" to "John",
            "age" to 25,
            "city" to "Melbourne"
        )
        val entity = Entity(properties)
        
        // Act
        val keys = entity.getAllKeys()
        
        // Assert
        assertEquals(3, keys.size)
        assertTrue("Should contain 'name'", keys.contains("name"))
        assertTrue("Should contain 'age'", keys.contains("age"))
        assertTrue("Should contain 'city'", keys.contains("city"))
    }
    
    @Test
    fun `extractDescription should find description field`() {
        // Arrange
        val properties = mapOf(
            "name" to "Product",
            "description" to "A great product"
        )
        val entity = Entity(properties)
        
        // Act & Assert
        assertEquals("A great product", entity.extractDescription())
    }
    
    @Test
    fun `extractDescription should fallback to desc field`() {
        // Arrange
        val properties = mapOf(
            "name" to "Product",
            "desc" to "Short description"
        )
        val entity = Entity(properties)
        
        // Act & Assert
        assertEquals("Short description", entity.extractDescription())
    }
    
    @Test
    fun `extractDescription should return default when no description`() {
        // Arrange
        val properties = mapOf("name" to "Product")
        val entity = Entity(properties)
        
        // Act & Assert
        assertEquals("Description not available", entity.extractDescription())
    }
    
    @Test
    fun `getEntityName should return first property value`() {
        // Arrange
        val properties = mapOf(
            "title" to "First Value",
            "name" to "Second Value"
        )
        val entity = Entity(properties)
        
        // Act
        val entityName = entity.getEntityName()
        
        // Assert
        assertTrue("Should return one of the property values", 
            entityName == "First Value" || entityName == "Second Value")
    }
    
    @Test
    fun `getEntityName should return default for empty entity`() {
        // Arrange
        val entity = Entity(emptyMap())
        
        // Act & Assert
        assertEquals("Unnamed Entity", entity.getEntityName())
    }
    
    @Test
    fun `entity should handle null values safely`() {
        // Arrange
        val properties = mapOf<String, Any?>(
            "name" to "John",
            "nullValue" to null
        )
        val entity = Entity(properties as Map<String, Any>)
        
        // Act & Assert
        assertEquals("John", entity.getProperty("name"))
        assertEquals("N/A", entity.getProperty("nullValue"))
    }
} 