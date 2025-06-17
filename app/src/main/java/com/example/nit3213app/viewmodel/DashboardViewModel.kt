package com.example.nit3213app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213app.data.model.Entity
import com.example.nit3213app.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * FILE 9
 * The DashboardViewModel manages the entity list for the main screen. It takes the keypass from login, fetches dashboard data from the API, and exposes the entity list through LiveData for the UI to observe.
 * This ViewModel works with my dynamic Entity system - it doesn't need to know what fields the entities contain. It just manages the list, and the UI automatically adapts to display whatever properties each entity has.
 * Next we'll look at the DashboardActivity.kt file
 
 
 
 
 
 * 
 * ViewModel for Dashboard screen following MVVM architecture
 * 
 * This class handles the business logic for loading dashboard data
 * and exposes it to the UI through LiveData objects.
 * 
 * @param repository The repository to handle data operations
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    
    // Private mutable LiveData for internal updates
    private val _entities = MutableLiveData<List<Entity>>()
    // Public read-only LiveData for UI observation
    val entities: LiveData<List<Entity>> = _entities
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    private val _entityTotal = MutableLiveData<Int>()
    val entityTotal: LiveData<Int> = _entityTotal
    
    /**
     * Load dashboard data from the API
     * This method is called when the screen is first loaded
     */
    fun loadDashboard(keypass: String) {
        _isLoading.value = true
        clearError()
        
        // Make API call using coroutines in ViewModel scope
        viewModelScope.launch {
            try {
                val response = repository.getDashboard(keypass)
                _isLoading.value = false
                
                if (response.isSuccessful) {
                    response.body()?.let { dashboardResponse ->
                        _entities.value = dashboardResponse.entities
                        _entityTotal.value = dashboardResponse.entityTotal
                    } ?: run {
                        _error.value = "Dashboard response is empty"
                    }
                } else {
                    _error.value = "Failed to load dashboard: ${response.message()}"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Network error: ${e.message}"
            }
        }
    }
    
    /**
     * Refresh the dashboard data
     * This can be called when user pulls to refresh
     */
    fun refreshDashboard(keypass: String) {
        loadDashboard(keypass)
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _error.value = null
    }
} 