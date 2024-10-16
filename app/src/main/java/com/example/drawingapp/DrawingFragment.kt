package com.example.drawingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.drawingapp.databinding.DrawingFragmentBinding
import com.example.drawingapp.DrawingViewModel
import com.example.drawingapp.DrawingCanvasView

class DrawingFragment : Fragment() {

    private var _binding: DrawingFragmentBinding? = null
    private val binding get() = _binding!!

    private val drawingViewModel: DrawingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DrawingFragmentBinding.inflate(inflater, container, false)

        setupListeners()
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
