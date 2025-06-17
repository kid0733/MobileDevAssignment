package com.example.nit3213app.repository

import com.example.nit3213app.data.api.ApiService
import com.example.nit3213app.data.model.DashboardResponse
import com.example.nit3213app.data.model.Entity
import com.example.nit3213app.data.model.LoginRequest
import com.example.nit3213app.data.model.LoginResponse
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FILE 6
 * The repository is like a librarian - UI asks it for data, and it knows how to get that data from the API. It provides a simple, clean interface and handles all the complex networking details.
 * I implement the repository pattern to abstract data sources from business logic. It provides a clean API for ViewModels, centralizes data access logic, and makes the app easily testable by allowing dependency injection of the data layer.
 * This sits at the boundary between the business logic layer and the data layer, following clean architecture principles. It allows me to change data sources without affecting UI.
 * Next we'll look at the ViewModel (DashboardViewModel.kt) - where the repository is used to fetch and display data!
 
 
 
 
 
 
 * Repository class that acts as the single source of truth for data
 * 
 * This class abstracts the data sources and provides a clean API
 * for the ViewModels to fetch data from network or local storage.
 * 
 * @param apiService The API service for network operations
 */
@Singleton
class MainRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    /**
     * Authenticate user with the provided credentials
     * 
     * @param username User's username
     * @param password User's password
     * @return Response containing LoginResponse or error
     */
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return try {
            // Use real API authentication
            val loginRequest = LoginRequest(username, password)
            apiService.login(loginRequest)
        } catch (e: Exception) {
            // Handle network errors or other exceptions
            throw e
        }
    }
    
    /**
     * Fetch dashboard data from the API using keypass
     * 
     * @param keypass The authentication key from login
     * @return Response containing DashboardResponse or error
     */
    suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return try {
            // Use the real API endpoint with keypass
            apiService.getDashboard(keypass)
        } catch (e: Exception) {
            // Handle network errors or other exceptions
            throw e
        }
    }
} 