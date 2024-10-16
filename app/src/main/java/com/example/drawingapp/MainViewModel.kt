package com.example.drawingapp.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // A list that holds all pictures
    val pictures = mutableListOf<String>()

    // Logic to add a new picture
    fun addPicture(pictureUri: String) {
        pictures.add(pictureUri)
    }

    // Logic to delete a picture
    fun deletePicture(index: Int) {
        if (index in pictures.indices) {
            pictures.removeAt(index)
        }
    }

    // Logic to handle sharing/editing to be added here eventually
}
