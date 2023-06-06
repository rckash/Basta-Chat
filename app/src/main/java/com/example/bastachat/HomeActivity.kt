package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bastachat.databinding.ActivityHomeBinding
import com.example.bastachat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize viewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        fAuth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        exitIfNotLoggedIn()

        binding.txtEmailHome.text = fAuth.currentUser?.email.toString()

        //on-click functionalities
        binding.btnLogOut.setOnClickListener {
            logOut()
            exitIfNotLoggedIn()
        }
    }

    private fun exitIfNotLoggedIn() {
        val currentUser = fAuth.currentUser
        if (currentUser == null) {
            val myIntent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }
}