package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bastachat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {  }
        binding.txtDontHaveAnAccountLogin.setOnClickListener { goToRegistration() }
        binding.txtSignUpLogin.setOnClickListener { goToRegistration() }
    }
    private fun goToRegistration() {
        val myIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(myIntent)
        finish()
    }
}