package com.example.nit3213app

import com.example.nit3213app.data.api.ApiService
import com.example.nit3213app.di.NetworkModule
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit

/**
 * Unit tests for NetworkModule
 * 
 * Tests that dependency injection creates the correct
 * network components with proper configuration.
 */
class NetworkModuleTest {

    private val networkModule = NetworkModule

    @Test
    fun `provideOkHttpClient should return non-null client`() {
        // Act
        val client = networkModule.provideOkHttpClient()
        
        // Assert
        assertNotNull("OkHttpClient should not be null", client)
        assertTrue("Should be instance of OkHttpClient", client is OkHttpClient)
    }

    @Test
    fun `provideOkHttpClient should have correct timeout settings`() {
        // Act
        val client = networkModule.provideOkHttpClient()
        
        // Assert
        assertEquals("Connect timeout should be 30 seconds", 30000, client.connectTimeoutMillis)
        assertEquals("Read timeout should be 30 seconds", 30000, client.readTimeoutMillis)
        assertEquals("Write timeout should be 30 seconds", 30000, client.writeTimeoutMillis)
    }

    @Test
    fun `provideOkHttpClient should have interceptors configured`() {
        // Act
        val client = networkModule.provideOkHttpClient()
        
        // Assert
        assertTrue("Should have interceptors", client.interceptors.isNotEmpty())
    }

    @Test
    fun `provideGson should return non-null Gson instance`() {
        // Act
        val gson = networkModule.provideGson()
        
        // Assert
        assertNotNull("Gson should not be null", gson)
        assertTrue("Should be instance of Gson", gson is Gson)
    }

    @Test
    fun `provideRetrofit should return non-null Retrofit instance`() {
        // Arrange
        val okHttpClient = networkModule.provideOkHttpClient()
        val gson = networkModule.provideGson()
        
        // Act
        val retrofit = networkModule.provideRetrofit(okHttpClient, gson)
        
        // Assert
        assertNotNull("Retrofit should not be null", retrofit)
        assertTrue("Should be instance of Retrofit", retrofit is Retrofit)
    }

    @Test
    fun `provideRetrofit should have correct base URL`() {
        // Arrange
        val okHttpClient = networkModule.provideOkHttpClient()
        val gson = networkModule.provideGson()
        
        // Act
        val retrofit = networkModule.provideRetrofit(okHttpClient, gson)
        
        // Assert
        val baseUrl = retrofit.baseUrl().toString()
        assertEquals("Base URL should match", "https://nit3213api.onrender.com/", baseUrl)
    }

    @Test
    fun `provideApiService should return non-null ApiService`() {
        // Arrange
        val okHttpClient = networkModule.provideOkHttpClient()
        val gson = networkModule.provideGson()
        val retrofit = networkModule.provideRetrofit(okHttpClient, gson)
        
        // Act
        val apiService = networkModule.provideApiService(retrofit)
        
        // Assert
        assertNotNull("ApiService should not be null", apiService)
        assertTrue("Should be instance of ApiService", apiService is ApiService)
    }

    @Test
    fun `provideGson should handle Entity serialization`() {
        // Arrange
        val gson = networkModule.provideGson()
        val jsonString = """{"name": "John", "age": 25}"""
        
        // Act & Assert - Should not throw exception
        try {
            val result = gson.fromJson(jsonString, com.example.nit3213app.data.model.Entity::class.java)
            assertNotNull("Entity should be created", result)
        } catch (e: Exception) {
            fail("Gson should handle Entity deserialization: ${e.message}")
        }
    }
} 