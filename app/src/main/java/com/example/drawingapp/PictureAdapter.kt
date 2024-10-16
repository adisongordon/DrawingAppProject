package com.example.drawingapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drawingapp.databinding.ItemPictureBinding

class PictureAdapter(private val pictures: List<String>) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    inner class PictureViewHolder(private val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureUri: String) {
            // Loads the images into the ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding = ItemPictureBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PictureViewHolder(binding)
    }
// Gets the picture
    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(pictures[position])
    }
// Sizes the picture 
    override fun getItemCount(): Int = pictures.size
}
