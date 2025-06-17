package com.example.nit3213app.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nit3213app.R
import com.example.nit3213app.data.model.Entity
import com.example.nit3213app.databinding.ActivityDashboardBinding
import com.example.nit3213app.ui.detail.DetailActivity
import com.example.nit3213app.ui.login.LoginActivity
import com.example.nit3213app.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * FILE 10
 * This is the main screen that shows the entity list. It observes the DashboardViewModel for data changes and automatically updates the RecyclerView. It handles loading, error, and empty states professionally.
 * The navigation to detail screen showcases my dynamic Entity system - it automatically passes all entity properties without knowing their structure in advance. This makes the app work with any API response format.
 * Next we'll look at the EntityAdapter.kt file
 
 
 
 
 
 * Dashboard Activity that displays a list of entities
 * 
 * This activity uses MVVM architecture with:
 * - ViewBinding for view references
 * - ViewModel for business logic
 * - RecyclerView with custom adapter
 * - LiveData for reactive UI updates
 * - Hilt for dependency injection
 */
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    
    // ViewBinding instance for accessing views
    private lateinit var binding: ActivityDashboardBinding
    
    // ViewModel instance injected by Hilt
    private val viewModel: DashboardViewModel by viewModels()
    
    // RecyclerView adapter
    private lateinit var entityAdapter: EntityAdapter
    
    // Keypass received from login
    private var keypass: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewBinding
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Get keypass from intent
        keypass = intent.getStringExtra("keypass")
        
        // Check if keypass is available
        if (keypass.isNullOrEmpty()) {
            // Handle error - no keypass provided
            binding.layoutError.visibility = View.VISIBLE
            binding.textViewError.text = "Authentication error: No keypass provided"
            return
        }
        
        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        
        // Set up RecyclerView
        setupRecyclerView()
        
        // Set up UI components
        setupUI()
        
        // Observe ViewModel data
        observeViewModel()
        
        // Load dashboard data with keypass
        viewModel.loadDashboard(keypass!!)
    }
    
    /**
     * Set up RecyclerView with adapter and layout manager
     */
    private fun setupRecyclerView() {
        entityAdapter = EntityAdapter { entity ->
            // Handle item click - navigate to detail screen
            navigateToDetail(entity)
        }
        
        binding.recyclerViewEntities.apply {
            adapter = entityAdapter
            layoutManager = LinearLayoutManager(this@DashboardActivity)
        }
    }
    
    /**
     * Set up UI components and click listeners
     */
    private fun setupUI() {
        // Set retry button click listener
        binding.buttonRetry.setOnClickListener {
            keypass?.let { viewModel.loadDashboard(it) }
        }
        
        // Set logout button click listener
        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }
    
    /**
     * Observe ViewModel LiveData for UI updates
     */
    private fun observeViewModel() {
        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            binding.loadingOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        // Observe entities list
        viewModel.entities.observe(this) { entities ->
            if (entities.isNotEmpty()) {
                // Show RecyclerView and hide other states
                binding.recyclerViewEntities.visibility = View.VISIBLE
                binding.layoutError.visibility = View.GONE
                binding.emptyLayout.visibility = View.GONE
                
                // Update adapter with new data
                entityAdapter.submitList(entities)
            } else {
                // Show empty state if no entities and not loading
                if (viewModel.isLoading.value != true) {
                    binding.recyclerViewEntities.visibility = View.GONE
                    binding.layoutError.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE
                }
            }
        }
        
        // Observe error state
        viewModel.error.observe(this) { error ->
            if (error != null) {
                // Show error layout
                binding.recyclerViewEntities.visibility = View.GONE
                binding.emptyLayout.visibility = View.GONE
                binding.layoutError.visibility = View.VISIBLE
                binding.textViewError.text = error
            } else {
                // Hide error layout
                binding.layoutError.visibility = View.GONE
            }
        }
        
        // Observe entity total (optional - for displaying count)
        viewModel.entityTotal.observe(this) { total ->
            // You can use this to show total count in toolbar subtitle
            supportActionBar?.subtitle = "Total: $total entities"
        }
    }
    
    /**
     * Navigate to Detail Activity with selected entity
     * 
     * @param entity The selected entity to display in detail
     */
    private fun navigateToDetail(entity: Entity) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            // Pass entity properties as extras
            entity.getAllKeys().forEachIndexed { index, key ->
                putExtra("entity_key_$index", key)
                putExtra("entity_value_$index", entity.getProperty(key))
            }
            putExtra("entity_keys_count", entity.getAllKeys().size)
            putExtra("keypass", keypass)
        }
        startActivity(intent)
    }
    
    /**
     * Create options menu with logout button
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }
    
    /**
     * Handle menu item selection
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    /**
     * Logout functionality - clear session and navigate to login
     */
    private fun logout() {
        // Clear any stored authentication data
        keypass = null
        
        // Navigate back to login
        val intent = Intent(this, LoginActivity::class.java).apply {
            // Clear the activity stack so user can't go back
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
} 