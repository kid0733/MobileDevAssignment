package com.example.nit3213app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213app.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * File 7
 * The LoginViewModel handles all the login business logic. It validates input, manages loading states, calls the repository for data, and exposes results through LiveData that the UI observes.
 * I implement MVVM with reactive programming using LiveData. The ViewModel maintains UI state, and uses coroutines for asynchronous operations. It provides a clear separation between UI and business logic while surviving configuration changes.
 * This demonstrates the Observer pattern with lifecycle awareness. The ViewModel is the single source of truth for the login state, and the UI reactively updates based on data changes.
 * Next we'll look at the LoginActivity.kt file
 * 
 * ViewModel for Login screen following MVVM architecture
 * 
 * This class handles the business logic for user authentication
 * and exposes data to the UI through LiveData objects.
 * 
 * @param repository The repository to handle data operations
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    
    // Private mutable LiveData for internal updates
    private val _loginResult = MutableLiveData<LoginResult?>()
    // Public read-only LiveData for UI observation
    val loginResult: LiveData<LoginResult?> = _loginResult
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    /**
     * Attempt to login with provided credentials
     * 
     * @param username User's username
     * @param password User's password
     */
    fun login(username: String, password: String) {
        // Input validation
        if (username.isBlank() || password.isBlank()) {
            _loginResult.value = LoginResult.Error("Username and password cannot be empty")
            return
        }
        
        // Set loading state
        _isLoading.value = true
        
        // Make API call using coroutines in ViewModel scope
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                _isLoading.value = false
                
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        _loginResult.value = LoginResult.Success(loginResponse.keypass)
                    } ?: run {
                        _loginResult.value = LoginResult.Error("Login response is empty")
                    }
                } else {
                    _loginResult.value = LoginResult.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _loginResult.value = LoginResult.Error("Network error: ${e.message}")
            }
        }
    }
    
    /**
     * Clear the login result (useful for navigation handling)
     */
    fun clearLoginResult() {
        _loginResult.value = null
    }
}

/**
 * Sealed class representing different states of login operation
 */
sealed class LoginResult {
    data class Success(val keypass: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
} 