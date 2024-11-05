package com.example.drawingapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
//import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.drawingapp.R
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.ui.main.MainFragment
import com.example.drawingapp.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (firstLaunch()) {
            showSplash()
        }
        else {
            showMain()
        }
    }

    private fun firstLaunch(): Boolean {
        val pref = getSharedPreferences("com.example.drawingapp", MODE_PRIVATE)
        return pref.getBoolean("firstLaunch", true).also {
            if (it) {
                pref.edit().putBoolean("firstLaunch", false).apply()
            }
        }
    }

    private fun showSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            runOnUiThread {
                showMain()
            }
        }, 3000)
    }

    private fun showMain() {
        setContentView(R.layout.activity_main)
        setListFragment()
//        setContent {
//            setMainView()
//        }
    }

    fun setDrawingFragment() {
        val drawingFragment = DrawingFragment()
        findViewById<View>(R.id.FCV1)?.visibility = View.VISIBLE
        findViewById<View>(R.id.FCV2)?.visibility = View.GONE
        findViewById<View>(R.id.FCV3)?.visibility = View.GONE
        findViewById<View>(R.id.FCV1)?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FCV1, drawingFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    private fun setListFragment() {
        val listFragment = DrawingListFragment()
        findViewById<View>(R.id.FCV1)?.visibility = View.GONE
        findViewById<View>(R.id.FCV2)?.visibility = View.GONE
        findViewById<View>(R.id.FCV3)?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FCV3, listFragment)
                .commit()
        }
    }

    @Composable
    fun setMainView() {

    }
}
