package com.example.drawingapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.drawingapp.R
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.ui.main.MainFragment
import com.example.drawingapp.ui.main.MainViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this);
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
        if (Firebase.auth.currentUser != null) {
            setListFragment(Firebase.auth.currentUser)
        } else {
            setContent {
                SetLoginView()
            }
        }
    }

    @Composable
    fun SetLoginView() {
        var user by remember { mutableStateOf(Firebase.auth.currentUser) }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }

        Column {
            if (user == null) {
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }
                LoginAndSignup(email, password, onLoginSuccess = {
                    user = it
                    setListFragment(user)
                }, onFail = {
                    errorMessage = it
                })
            } else {
                LaunchedEffect(user) {
                    setListFragment(user)
                }
            }
        }
    }


    @Composable
    fun LoginAndSignup(
        initialEmail: String,
        initialPassword: String,
        onLoginSuccess: (FirebaseUser) -> Unit,
        onFail: (String) -> Unit
    ) {
        var email by remember { mutableStateOf(initialEmail) }
        var password by remember { mutableStateOf(initialPassword) }

        Column {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Row {
                Button(onClick = { login(email, password, onLoginSuccess, onFail) }) {
                    Text("Log In")
                }
                Button(onClick = { signUp(email, password, onLoginSuccess, onFail) }) {
                    Text("Sign Up")
                }
            }
        }
    }


    private fun login(email: String, password: String, onSuccess: (FirebaseUser) -> Unit, onFail: (String) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(Firebase.auth.currentUser!!)
                    showMain()
                } else {
                    onFail(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun logout() {
        Firebase.auth.signOut()
        showMain()
    }

    private fun signUp(email: String, password: String, onSuccess: (FirebaseUser) -> Unit, onFail: (String) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(Firebase.auth.currentUser!!)
                    showMain()
                } else {
                    onFail(task.exception?.message ?: "Signup failed")
                }
            }
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

    private fun setListFragment(user: FirebaseUser?) {
        val listFragment = DrawingListFragment()
        findViewById<View>(R.id.FCV1)?.visibility = View.GONE
        findViewById<View>(R.id.FCV2)?.visibility = View.GONE
        user?.let {
            val fragment = DrawingListFragment().apply {
                arguments = Bundle().apply {
                    putString("userId", user.email)
                }
            }
            findViewById<View>(R.id.FCV3)?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FCV3, fragment)
                    .commit()
            }
        }

    }

}
