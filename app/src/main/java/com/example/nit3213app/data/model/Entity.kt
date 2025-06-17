package com.example.nit3213app.data.model

/**
 * FILE 2
 * Instead of hardcoding specific data fields, I created a universal Entity model that adapts to any JSON structure the API sends.
 * I use Map<String, Any> to store dynamic key-value pairs, with safe type conversion methods that ensure the UI never crashes on unexpected data types.
 * This makes the app completely future-proof. When the API adds new fields, they automatically appear in the UI without requiring app updates.
 * 
 * Next we'll look at ApiService.kt
 
 
 
 
 
 * Dynamic entity model that handles flexible JSON data structures
 * Uses a map-based approach to store arbitrary key-value pairs from API responses
 * 
 * @param properties Map storing all entity data as key-value pairs
 */
data class Entity(
    val properties: Map<String, Any>
) {
    /**
     * Retrieves a value for the given key, with fallback to default
     * getProperty(): "Gets any information and makes it safe to display"
     */
    fun getProperty(key: String, fallback: String = "N/A"): String {
        return when (val data = properties[key]) {
            is String -> data
            is Number -> data.toString()
            is Boolean -> data.toString()
            null -> fallback
            else -> data.toString()
        }
    }
    
    /**
     * Returns all available property keys
     * What it does: Gets a list of all the field names available in the entity
     * getAllKeys(): "Shows me what information is available"
     * 
     */
    fun getAllKeys(): List<String> = properties.keys.toList()
    
    /**
     * Attempts to find and return description content
     * Searches multiple possible description field names
     * extractDescription(): "Smartly finds description text using common field names"

     */
    fun extractDescription(): String {
        val descValue = getProperty("description") 
        if (descValue != "N/A") return descValue
        
        val descShort = getProperty("desc")
        if (descShort != "N/A") return descShort
        
        return "Description not available"
    }
    
    /**
     * Gets a representative name for this entity
     * getEntityName(): "Gives each entity a display name"
     */
    fun getEntityName(): String {
        val availableKeys = getAllKeys()
        return if (availableKeys.isNotEmpty()) {
            getProperty(availableKeys.first())
        } else {
            "Unnamed Entity"
        }
    }
} 