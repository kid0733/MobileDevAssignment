package com.example.nit3213app.data.model

/**
 * Data class representing the dashboard response from the API
 * 
 * @param entities List of entities returned from the dashboard
 * @param entityTotal Total number of entities
 */
data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Int
) 