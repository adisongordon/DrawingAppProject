package com.example.drawingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.compose.ui.semantics.text
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drawingapp.databinding.DrawingFragmentBinding
import com.example.drawingapp.DrawingViewModel
import com.example.drawingapp.DrawingCanvasView
import java.io.File

class DrawingFragment : Fragment() {

    private var _binding: DrawingFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawingViewModel: DrawingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DrawingFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawingViewModel = ViewModelProvider(requireActivity()).get(DrawingViewModel::class.java)
        val saveButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_save)
        setupListeners(view)
        shareDrawing(view)

        saveButton.setOnClickListener {
            saveDrawing()
        }
    }

    // Set up listeners
    private fun setupListeners(view: View) {
        val colorBtn: Button = view.findViewById(R.id.btn_color)
        val sizeBtn: Button = view.findViewById(R.id.btn_pen)
        val eraseBtn: Button = view.findViewById(R.id.btn_eraser)
        sizeBtn.setOnClickListener {
            binding.canvasView.setPenSize(10f)

            //binding.canvasView.setPenColor(drawingViewModel.selectedColor.value ?: 0xFF000000.toInt())
        }

        colorBtn.setOnClickListener {
            drawingViewModel.selectColor(0x00000000.toInt())  // Example: Red color
        }
        eraseBtn.setOnClickListener {
            drawingViewModel.selectColor(0xFFFFFFFF.toInt())  // Example: Red color
        }
    }

    // Set up ViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        drawingViewModel = ViewModelProvider(requireActivity()).get(DrawingViewModel::class.java)

        // setting up drawing view to draw
        val drawingView = view?.findViewById<DrawingCanvasView>(R.id.canvas_view)
        drawingView?.setViewModel(drawingViewModel)

    }

    // Handle back button press
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Save drawing to RoomDB
    fun saveDrawing() {
        val currentBitmap = drawingViewModel.bitmap.value
        if (currentBitmap != null) {
            val drawingTitle = binding.drawingTitle.text.toString() // Get title from EditText
            val filename = drawingViewModel.saveImage(requireContext(), currentBitmap, drawingTitle) // Pass title
            val file = File(requireContext().filesDir, filename)
            drawingViewModel.fileNameList.add(filename)
            drawingViewModel.insertIntoDB(requireContext(), filename, file.absolutePath) // Update insertIntoDB
            Toast.makeText(requireContext(), "Drawing saved to $filename", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Error, No drawing to save", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareDrawing(view: View) {
        val shareButton: Button = view.findViewById(R.id.btn_share)
        shareButton.setOnClickListener{
            Toast.makeText(context, "Image has been shared", Toast.LENGTH_SHORT).show()
        }
    }
}
