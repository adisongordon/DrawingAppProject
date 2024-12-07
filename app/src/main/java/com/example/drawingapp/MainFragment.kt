package com.example.drawingapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drawingapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.drawingapp.DrawingFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth


class MainFragment : Fragment() {
// Creates all of the instances of the buttons and recyclerview
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddDrawing: FloatingActionButton
    private lateinit var btnShare: Button
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
// Creates the view for the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        fabAddDrawing = view.findViewById(R.id.fabAddDrawing)
        btnShare = view.findViewById(R.id.btnShare)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnDelete = view.findViewById(R.id.btnDelete)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fabAddDrawing.setOnClickListener {
            val intent = Intent(requireContext(), DrawingFragment::class.java)
            startActivity(intent)
        }
        btnShare.setOnClickListener { handleShare() }
        btnEdit.setOnClickListener { handleEdit() }
        btnDelete.setOnClickListener { handleDelete() }
    }
// Sets up the recycler
    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val adapter = PictureAdapter(viewModel.pictures)
        recyclerView.adapter = adapter
    }

    private fun handleShare() {
        // Handles share functionality
    }

    private fun handleEdit() {
        // Handles edit functionality
    }

    private fun handleDelete() {
        // Handles delete functionality
    }


}