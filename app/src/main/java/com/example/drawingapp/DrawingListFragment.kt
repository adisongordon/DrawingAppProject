package com.example.drawingapp

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class DrawingListFragment : Fragment() {
    private val viewModel: DrawingViewModel by activityViewModels()

    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DrawingListScreen(viewModel, {})
            }
        }
   }

    @Composable
    fun DrawingListScreen(viewModel: DrawingViewModel, onNewDrawing: () -> Unit) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    onClick = { (activity as? MainActivity)?.setDrawingFragment() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(3.dp)
                ) {
                    Text("Create New Drawing")
                }

                val bitmaps = viewModel.bitmaps.observeAsState(initial = emptyList())

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 70.dp)
                ) {
                    items(bitmaps.value) { bitmap ->
                        BitmapItem(bitmap) { onNewDrawing() }
                    }
                }
            }
        }
    }

    @Composable
    fun BitmapItem(bitmap: Bitmap, onClick: () -> Unit) {
        Card(
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .padding(10.dp)
                .size(120.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            val imageBitmap = bitmap.asImageBitmap()

            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}