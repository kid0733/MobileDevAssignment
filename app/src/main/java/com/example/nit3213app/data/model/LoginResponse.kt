package com.example.nit3213app.data.model

/**
 * Data class representing the login response from the API
 * 
 * @param keypass The authentication key returned after successful login
 */
data class LoginResponse(
    val keypass: String
) 