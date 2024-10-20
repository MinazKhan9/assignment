package com.example.myassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myassignment.Model.UserModel
import com.example.myassignment.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private var userName: String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.continueButton.setOnClickListener {
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter the details", Toast.LENGTH_SHORT).show()
            } else {
                createUser()
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUi(user)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserData()
                        val user = auth.currentUser
                        updateUi(user)
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).setValue(user)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}