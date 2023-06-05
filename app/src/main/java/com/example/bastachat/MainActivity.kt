package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bastachat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //on-click functionalities
        binding.txtAlreadyHaveAnAccount.setOnClickListener { goToLoginActivity() }
        binding.txtLogin.setOnClickListener { goToLoginActivity() }
        binding.btnJoinNow.setOnClickListener { goToRegisterActivity() }
    }
    private fun goToLoginActivity() {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }
    private fun goToRegisterActivity() {
        val myIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(myIntent)
        finish()
    }
}