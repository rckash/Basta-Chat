package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.bastachat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinding instantiation
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //firebase authentication instantiation
        fAuth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = fAuth.currentUser
        if (currentUser != null) {
            val myIntent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(myIntent)
            finish()
        }

        //on-click functionalities
        binding.btnLogin.setOnClickListener { login() }
        binding.txtDontHaveAnAccountLogin.setOnClickListener { goToRegistrationActivity() }
        binding.txtSignUpLogin.setOnClickListener { goToRegistrationActivity() }
    }
    private fun goToRegistrationActivity() {
        val myIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(myIntent)
        finish()
    }
    private fun login() {
        //input
        val emailLogin = binding.edtEmailLogin.text.toString()
        val passwordLogin = binding.edtPasswordLogin.text.toString()
        //login process
        fAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user
                    val myIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(myIntent)
                    finish()
                } else {
                    val toastText = "Incorrect Credential/s"
                    val myToast = Toast.makeText(this@LoginActivity, toastText, 3)
                    myToast.show()
                }
            }
    }
}