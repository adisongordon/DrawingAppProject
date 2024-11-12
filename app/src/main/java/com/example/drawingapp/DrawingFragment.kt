package com.example.drawingapp

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.compose.material3.Button
import androidx.fragment.app.activityViewModels
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
    ): View? {
        _binding = DrawingFragmentBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawingViewModel = ViewModelProvider(requireActivity()).get(DrawingViewModel::class.java)
        val saveButton = view.findViewById<android.widget.Button>(R.id.btn_save)

        saveButton.setOnClickListener {
            saveDrawing()
        }
    }

    private fun setupListeners() {
        binding.btnPen.setOnClickListener {
            binding.canvasView.setPenSize(10f)
            binding.canvasView.setPenColor(drawingViewModel.selectedColor.value ?: 0xFF000000.toInt())
        }

        binding.btnColor.setOnClickListener {
            drawingViewModel.selectColor(0xFFFF0000.toInt())  // Example: Red color
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        drawingViewModel = ViewModelProvider(requireActivity()).get(DrawingViewModel::class.java)

        // setting up drawing view to draw
        val drawingView = view?.findViewById<DrawingCanvasView>(R.id.canvas_view)
        drawingView?.setViewModel(drawingViewModel)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun saveDrawing() {
        val currentBitmap = drawingViewModel.bitmap.value
        if (currentBitmap != null) {
            // Upload this filename & currentBitmap to RoomDB
            val filename = drawingViewModel.saveImage(requireContext(), currentBitmap)
            val file = File(requireContext().filesDir, filename)
            drawingViewModel.fileNameList.add(filename)
            drawingViewModel.insertIntoDB(requireContext(), filename, file.absolutePath)
        } else {
            Toast.makeText(requireContext(), "Error, No drawing to save", Toast.LENGTH_SHORT).show()
        }
    }
}
