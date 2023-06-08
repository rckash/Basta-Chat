package com.example.bastachat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.createBitmap
import com.example.bastachat.databinding.ActivityHomeBinding
import com.google.android.play.integrity.internal.c
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.InputStream


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var fAuth: FirebaseAuth
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK) {
            Log.d("HomeActivity","Activity result: ${it.data}")
            contentResolver

            val inputStream = contentResolver.openInputStream(intent.data!!)
            val uri = intent.data
            Log.d("HomeActivity","URI is: $uri")
            val myDrawable = Drawable.createFromStream(inputStream, uri.toString())
            binding.btnUploadPhoto.background = uri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize viewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        fAuth = Firebase.auth

        binding.txtEmailHome.text = fAuth.currentUser?.email.toString()

        //on-click functionalities
        binding.btnLogOut.setOnClickListener {
            logOut()
            exitIfNotLoggedIn()
        }
        binding.btnUploadPhoto.setOnClickListener {
            Log.d("HomeActivity","try to show photo selector")

            // Launch the photo picker and let the user choose only images.
            openImageSelector()
        }
    }
    override fun onStart() {
        super.onStart()
        exitIfNotLoggedIn()
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
        Log.d("HomeActivity","Logging out")
    }
    private fun openImageSelector() {
        val myImageSelectorIntent = Intent(Intent.ACTION_PICK)
        myImageSelectorIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        launcher.launch(myImageSelectorIntent)



    }
}