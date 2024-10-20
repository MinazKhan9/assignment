package com.example.myassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myassignment.Model.UserModel
import com.example.myassignment.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var username: String
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
            username = binding.userName.text.toString()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isEmpty() || password.isBlank() || username.isBlank()) {
                Toast.makeText(this, "Please fill up all the details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }
        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }

    }

    private fun saveUserData() {
        username = binding.userName.text.toString()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(username, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)


    }
}