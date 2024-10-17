package com.example.drawingapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.drawingapp.R
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.ui.main.MainFragment
import com.example.drawingapp.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DrawingApp)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.commit{
            replace<MainFragment>(R.id.FCV)
        }
        setContentView(binding.root)


    }
}
