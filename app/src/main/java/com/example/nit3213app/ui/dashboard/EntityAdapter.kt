package com.example.nit3213app.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213app.data.model.Entity
import com.example.nit3213app.databinding.ItemEntityBinding

/**
 * RecyclerView Adapter for displaying Entity items in the dashboard
 * 
 * Uses ListAdapter for efficient list updates with DiffUtil
 * ViewBinding is used to avoid findViewById calls
 * 
 * @param onItemClick Callback function when an item is clicked
 */
class EntityAdapter(
    private val onItemClick: (Entity) -> Unit
) : ListAdapter<Entity, EntityAdapter.EntityViewHolder>(EntityDiffCallback()) {
    
    /**
     * Create new ViewHolder instances
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EntityViewHolder(binding)
    }
    
    /**
     * Bind data to ViewHolder
     */
    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val entity = getItem(position)
        holder.bind(entity)
    }
    
    /**
     * ViewHolder class that holds the view references
     * 
     * @param binding ViewBinding for the item layout
     */
    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        /**
         * Bind entity data to the views
         * 
         * @param entity The entity to display
         */
        fun bind(entity: Entity) {
            binding.apply {
                val availableKeys = entity.getAllKeys()
                
                // Show first property
                textProperty1.text = if (availableKeys.isNotEmpty()) {
                    "Property 1: ${entity.getProperty(availableKeys[0])}"
                } else {
                    "No data found"
                }
                
                // Show second property
                textProperty2.text = if (availableKeys.size > 1) {
                    "Property 2: ${entity.getProperty(availableKeys[1])}"
                } else {
                    ""
                }
                
                // Show description preview
                textDescription.text = entity.extractDescription()
                
                // Handle item clicks
                root.setOnClickListener {
                    onItemClick(entity)
                }
            }
        }
    }
    
    /**
     * DiffUtil callback for efficient list updates
     * 
     * This tells the RecyclerView which items have changed
     * so it can animate updates efficiently
     */
    private class EntityDiffCallback : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean {
            // Compare entity names for identity
            return oldItem.getEntityName() == newItem.getEntityName()
        }
        
        override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean {
            return oldItem == newItem
        }
    }
} 