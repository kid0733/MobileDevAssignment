package com.example.nit3213app.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.nit3213app.R
import com.example.nit3213app.databinding.ActivityDetailBinding
import com.example.nit3213app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Detail Activity that displays complete information about a selected entity
 * 
 * This activity receives entity data through Intent extras and displays
 * all properties in a detailed view with cards for better presentation.
 */
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    
    // ViewBinding instance for accessing views
    private lateinit var binding: ActivityDetailBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewBinding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Set up toolbar with back navigation
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        // Set up logout button
        binding.buttonLogout.setOnClickListener {
            logout()
        }
        
        // Get entity data from intent and display it
        displayEntityData()
    }
    
    /**
     * Handle back button press in toolbar
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    
    /**
     * Extract entity data from intent extras and display in UI
     */
    private fun displayEntityData() {
        // Get entity data from intent extras
        val keysCount = intent.getIntExtra("entity_keys_count", 0)
        val keypass = intent.getStringExtra("keypass") // Optional, for future use
        
        // Separate properties and descriptions
        val propertiesBuilder = StringBuilder()
        val descriptionsBuilder = StringBuilder()
        var firstKey = ""
        var propertyCount = 1
        
        for (i in 0 until keysCount) {
            val key = intent.getStringExtra("entity_key_$i") ?: continue
            val value = intent.getStringExtra("entity_value_$i") ?: "N/A"
            
            if (i == 0) firstKey = value // Use first value for title
            
            // Check if this is a description field
            if (key.lowercase().contains("description") || key.lowercase().contains("desc")) {
                // Add to descriptions section
                if (descriptionsBuilder.isNotEmpty()) {
                    descriptionsBuilder.append("\n\n")
                }
                descriptionsBuilder.append("Description: $value")
            } else {
                // Add to properties section
                propertiesBuilder.append("Property $propertyCount: $value\n\n")
                propertyCount++
            }
        }
        
        // Combine properties first, then descriptions
        val finalText = StringBuilder()
        if (propertiesBuilder.isNotEmpty()) {
            finalText.append(propertiesBuilder.toString().trimEnd())
        }
        if (descriptionsBuilder.isNotEmpty()) {
            if (finalText.isNotEmpty()) {
                finalText.append("\n\n")
            }
            finalText.append(descriptionsBuilder.toString())
        }
        
        // Set data to views
        binding.apply {
            textDescription.text = finalText.toString()
        }
        
        // Update toolbar title with first key value or default
        supportActionBar?.title = if (firstKey.isNotEmpty()) firstKey else "Entity Details"
    }
    
    /**
     * Create options menu with logout button
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
        // Navigate back to login
        val intent = Intent(this, LoginActivity::class.java).apply {
            // Clear the activity stack so user can't go back
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
} 