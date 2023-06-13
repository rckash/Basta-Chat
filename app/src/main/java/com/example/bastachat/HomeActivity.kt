package com.example.bastachat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bastachat.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var fAuth: FirebaseAuth

    private var selectedPhotoUri: Uri? = null
    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            selectedPhotoUri = it
            Log.d("HomeActivity", selectedPhotoUri.toString())
            binding.imageView.setImageURI(selectedPhotoUri)
        }
    )

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
            openImageSelector()
        }
        binding.btnUploadToFirebase.setOnClickListener {
            Log.d("HomeActivity","try to upload photo to Firebase")
            uploadImageToFirebaseStorage()
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
        getImage.launch("image/*")
    }
    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) { return }

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("HomeActivity","Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("RegisterActivity", "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
    }
    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val usernameListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val username = dataSnapshot.getValue()
                binding.btnUploadPhoto.text = username.toString()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        ref.addValueEventListener(usernameListener)
        val username = ref.addValueEventListener(usernameListener).toString()




//        FirebaseAuth.getInstance().currentUser.toString()

        val user = User(uid, username, profileImageUrl)
        Log.d("HomeActivity","username is: $username")

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegistrationActivity", "Saved username to Firebase Database")
            }
            .addOnFailureListener {
                Log.d("RegistrationActivity","Save to Firebase Database failed")
            }
    }

}
