package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.bastachat.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewbinding instantiation
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //firebase instantiation
        fAuth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = fAuth.currentUser
        if (currentUser != null) {
            val myIntent = Intent(this@RegistrationActivity, HomeActivity::class.java)
            startActivity(myIntent)
            finish()
        }

        //on-click functionalities
        binding.btnSignUp.setOnClickListener {
            val emailRegistration = binding.edtEmailRegistration.text.toString()
            val passwordRegistration = binding.edtPasswordRegistration.text.toString()
            signUp(emailRegistration, passwordRegistration)
        }
        binding.txtAlreadyHaveAnAccountRegistration.setOnClickListener { goToLoginActivity() }
        binding.txtLoginRegistration.setOnClickListener { goToLoginActivity() }
    }
    private fun goToLoginActivity() {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }
    private fun signUp(emailRegistration: String, passwordRegistration: String) {
        fAuth.createUserWithEmailAndPassword(emailRegistration, passwordRegistration)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home
                    val myIntent = Intent(this@RegistrationActivity, HomeActivity::class.java)
                    startActivity(myIntent)
                    finish()
                } else {
                    val toastText = "Incorrect Credential/s"
                    val myToast = Toast.makeText(this@RegistrationActivity, toastText, 3)
                    myToast.show()
                }
            }
    }
}