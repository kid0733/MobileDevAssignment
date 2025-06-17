package com.example.nit3213app.di

import com.example.nit3213app.data.api.ApiService
import com.example.nit3213app.data.model.Entity
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Hilt dependency injection module for network components
 * 
 * Manages the lifecycle and configuration of network-related dependencies
 * including HTTP clients, JSON parsing, and API service interfaces
 * 
 *  This file tells Hilt how to create all my network components. It's like a recipe book that builds HTTP clients, JSON converters, and API services automatically.
 * 
 * Next we'll look at Entity.kt
 
 
 
 
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    /**
     * API server base URL
     */
    private const val API_BASE_URL = "https://nit3213api.onrender.com/"
    
    /**
     * Provides OkHttpClient with logging interceptor for debugging network requests
     * 
     * @return Configured OkHttpClient instance
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        // Custom DNS resolver to handle resolution issues
        val customDns = object : Dns {
            override fun lookup(hostname: String): List<InetAddress> {
                return when (hostname) {
                    "nit3213api.onrender.com" -> {
                        try {
                            // Try to resolve using system DNS first
                            Dns.SYSTEM.lookup(hostname)
                        } catch (e: Exception) {
                            // Fallback to known IP addresses if DNS fails
                            listOf(
                                InetAddress.getByName("216.24.57.4"),
                                InetAddress.getByName("216.24.57.252")
                            )
                        }
                    }
                    else -> Dns.SYSTEM.lookup(hostname)
                }
            }
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .dns(customDns)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    
    /**
     * Custom Gson instance with Entity deserializer
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Entity::class.java, EntityDeserializer())
            .create()
    }
    
    /**
     * Provides Retrofit instance configured with base URL and custom Gson converter
     * 
     * @param okHttpClient The OkHttpClient to use for network requests
     * @param gson Custom Gson instance with deserializers
     * @return Configured Retrofit instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    
    /**
     * Provides ApiService implementation
     * 
     * @param retrofit The Retrofit instance to create the service from
     * @return ApiService implementation
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

/**
 * Custom deserializer for Entity class that can handle any JSON structure
 */
class EntityDeserializer : JsonDeserializer<Entity> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Entity {
        val jsonObject = json?.asJsonObject ?: JsonObject()
        val entityData = mutableMapOf<String, Any>()
        
        // Convert all JSON properties to a Map
        for ((key, value) in jsonObject.entrySet()) {
            entityData[key] = when {
                value.isJsonPrimitive -> {
                    val primitive = value.asJsonPrimitive
                    when {
                        primitive.isString -> primitive.asString
                        primitive.isNumber -> primitive.asNumber
                        primitive.isBoolean -> primitive.asBoolean
                        else -> primitive.asString
                    }
                }
                value.isJsonNull -> "N/A"
                else -> value.toString()
            }
        }
        
        return Entity(entityData)
    }
} 