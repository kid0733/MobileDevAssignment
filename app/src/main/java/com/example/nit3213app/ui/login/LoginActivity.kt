package com.example.nit3213app.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nit3213app.databinding.ActivityLoginBinding
import com.example.nit3213app.ui.dashboard.DashboardActivity
import com.example.nit3213app.viewmodel.LoginResult
import com.example.nit3213app.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress

/**
 * FILE 8
 * This is the login screen that users see. It observes the LoginViewModel for data changes and automatically updates the UI. When users click login, it passes the credentials to the ViewModel and reacts to the results.

 * This demonstrates the separation of concerns in MVVM - the Activity only handles UI events and observation, while all business logic lives in the ViewModel. The reactive observers ensure the UI stays in sync with the data layer.
 * Next we'll look at the DashboardActivity.kt file
 
 
 
 
 
 * Authentication Activity for user login
 * 
 * Implements secure user authentication using:
 * - Data binding for UI elements
 * - ViewModel pattern for data management
 * - Observer pattern for reactive updates
 * - Dependency injection via Hilt
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    
    // Activity view binding
    private lateinit var viewBinding: ActivityLoginBinding
    
    // Authentication ViewModel
    private val authViewModel: LoginViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize view binding
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        // Configure UI elements
        initializeComponents()
        
        // Set up data observers
        setupObservers()
        
        // Check network connectivity
        checkNetworkConnection()
    }
    
    /**
     * Verify network connectivity and DNS resolution
     */
    private fun checkNetworkConnection() {
        lifecycleScope.launch {
            try {
                val status = withContext(Dispatchers.IO) {
                    try {
                        // Verify Google DNS
                        val googleAddress = InetAddress.getByName("google.com")
                        Log.d("NetworkCheck", "Google resolved: ${googleAddress.hostAddress}")
                        
                        // Verify API server DNS
                        val apiAddress = InetAddress.getByName("nit3213api.onrender.com")
                        Log.d("NetworkCheck", "API resolved: ${apiAddress.hostAddress}")
                        
                        "Network connectivity verified"
                    } catch (e: Exception) {
                        Log.e("NetworkCheck", "Network verification failed: ${e.message}")
                        "Network verification failed: ${e.message}"
                    }
                }
                Log.d("NetworkCheck", status)
            } catch (e: Exception) {
                Log.e("NetworkCheck", "Connection check error: ${e.message}")
            }
        }
    }
    
    /**
     * Configure UI components and event handlers
     */
    private fun initializeComponents() {
        // Set up login button action
        viewBinding.buttonLogin.setOnClickListener {
            val userInput = viewBinding.editTextUsername.text.toString().trim()
            val passInput = viewBinding.editTextPassword.text.toString().trim()
            
            // Trigger authentication process
            authViewModel.login(userInput, passInput)
        }
    }
    
    /**
     * Set up reactive data observers
     */
    private fun setupObservers() {
        // Monitor loading state
        authViewModel.isLoading.observe(this) { loading ->
            viewBinding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            viewBinding.buttonLogin.isEnabled = !loading
        }
        
        // Monitor authentication results
        authViewModel.loginResult.observe(this) { result ->
            when (result) {
                is LoginResult.Success -> {
                    // Hide error display
                    viewBinding.errorCard.visibility = View.GONE
                    
                    // Proceed to dashboard
                    proceedToDashboard(result.keypass)
                    
                    // Clear result state
                    authViewModel.clearLoginResult()
                }
                is LoginResult.Error -> {
                    // Display error information
                    viewBinding.textViewError.text = result.message
                    viewBinding.errorCard.visibility = View.VISIBLE
                    
                    // Show user notification
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
                null -> {
                    // Hide error when cleared
                    viewBinding.errorCard.visibility = View.GONE
                }
            }
        }
    }
    
    /**
     * Navigate to main dashboard screen
     * 
     * @param keypass Authentication token for API access
     */
    private fun proceedToDashboard(keypass: String) {
        val dashboardIntent = Intent(this, DashboardActivity::class.java).apply {
            putExtra("keypass", keypass)
        }
        startActivity(dashboardIntent)
        
        // Close login screen
        finish()
    }
} 