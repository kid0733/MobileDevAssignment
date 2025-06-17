package com.example.nit3213app

import com.example.nit3213app.data.model.LoginRequest
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for LoginRequest data model
 * 
 * Tests the basic functionality of the LoginRequest data class
 * to ensure fields are properly set and accessible.
 */
class LoginRequestTest {

    @Test
    fun `login request fields should be set correctly`() {
        // Arrange
        val username = "Siddhant"
        val password = "s8091542"
        
        // Act
        val request = LoginRequest(username, password)
        
        // Assert
        assertEquals("Username should match", username, request.username)
        assertEquals("Password should match", password, request.password)
    }
    
    @Test
    fun `login request with empty strings should work`() {
        // Arrange & Act
        val request = LoginRequest("", "")
        
        // Assert
        assertEquals("Empty username should be allowed", "", request.username)
        assertEquals("Empty password should be allowed", "", request.password)
    }
    
    @Test
    fun `login request with special characters should work`() {
        // Arrange
        val username = "test@example.com"
        val password = "P@ssw0rd123!"
        
        // Act
        val request = LoginRequest(username, password)
        
        // Assert
        assertEquals("Special characters in username should work", username, request.username)
        assertEquals("Special characters in password should work", password, request.password)
    }
} 