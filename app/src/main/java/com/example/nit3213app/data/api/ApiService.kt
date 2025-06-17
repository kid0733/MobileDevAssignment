package com.example.nit3213app.data.api

import com.example.nit3213app.data.model.DashboardResponse
import com.example.nit3213app.data.model.LoginRequest
import com.example.nit3213app.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * FILE 4
 * This interface defines the two API calls my app makes: login with credentials to get a keypass, and fetch dashboard data using that keypass
 * I use Retrofit annotations to declare REST API endpoints. The @POST and @GET annotations define HTTP methods, @Body sends JSON request data, and @Path creates dynamic URLs. Retrofit automatically generates the implementation of this interface.
 * This sits at the bottom of my data layer. The repository calls these functions, ViewModels call the repository, and Activities observe the ViewModels.
 * Next we'll look at the Data Models (LoginRequest, LoginResponse, DashboardResponse) - the objects that flow through these API calls!
 * 
 * These are the message formats for talking to the API. LoginRequest packages credentials, LoginResponse contains the keypass, and DashboardResponse holds the entity data.
 * I use data classes as Data Transfer Objects (DTOs) for API communication. They provide type safety, immutability, and work seamlessly with Gson for JSON serialization. Each model represents a specific API contract.
 * These models sit at the boundary between my app and the external API, ensuring type-safe data transfer and clear contracts for each endpoint.
 * Next we'll look at the  MainRepository.kt - where these models come together to provide a clean data access layer!
 
 
 Next we'll see DashboardViewModel.kt - the business logic for the dashboard screen that handles entity lists!
 
 
 
 
 * Retrofit API service interface defining all the network endpoints
 * 
 * This interface declares the HTTP operations that can be performed.
 * Retrofit will generate the implementation of this interface.
 */
interface ApiService {
    
    /**
     * Authenticate user with username and password
     * 
     * @param loginRequest Contains username and password
     * @return LoginResponse containing the keypass
     */
    @POST("sydney/auth")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    
    /**
     * Fetch dashboard data using keypass
     * 
     * @param keypass The authentication key returned from login
     * @return DashboardResponse containing list of entities and total count
     */
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): Response<DashboardResponse>
} 