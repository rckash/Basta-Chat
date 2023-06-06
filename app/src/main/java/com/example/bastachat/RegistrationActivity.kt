package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bastachat.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {  }
        binding.txtAlreadyHaveAnAccountRegistration.setOnClickListener { goToLoginPage() }
        binding.txtLoginRegistration.setOnClickListener { goToLoginPage() }
    }
    private fun goToLoginPage() {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }
}