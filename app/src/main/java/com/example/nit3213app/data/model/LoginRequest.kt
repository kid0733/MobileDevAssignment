package com.example.nit3213app.data.model

/**
 * Data class representing the login request body
 * 
 * @param username The username for authentication
 * @param password The password for authentication
 */
data class LoginRequest(
    val username: String,
    val password: String
) 