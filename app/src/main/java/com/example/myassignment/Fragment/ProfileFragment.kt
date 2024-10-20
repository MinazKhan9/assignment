package com.example.myassignment.Fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.myassignment.LoginActivity
import com.example.myassignment.MainActivity
import com.example.myassignment.Model.ProfileInfo
import com.example.myassignment.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firstname: String
    private lateinit var lastname: String
    private lateinit var profession: String
    private lateinit var cityname: String
    private lateinit var dob: String
    private var gender: String = "female"
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        database = Firebase.database.reference

        saveUserData()
        val textView: TextView = binding.dob
        textView.text = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
        val c = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd-MM-yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textView.text = sdf.format(c.time)

            }
        textView.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.male.setOnClickListener {
            gender = "male"
            binding.female.isChecked = false
            binding.others.isChecked = false
        }
        binding.female.setOnClickListener {
            gender = "female"
            binding.male.isChecked = false
            binding.others.isChecked = false
        }
        binding.others.setOnClickListener {
            gender = "others"
            binding.male.isChecked = false
            binding.female.isChecked = false
        }

        binding.apply {
            firstName.isEnabled = false
            lastName.isEnabled = false
            gender.isEnabled = false
            dob.isEnabled = false
            profession.isEnabled = false
            cityName.isEnabled = false

            binding.edit.setOnClickListener {
                firstName.isEnabled = !firstName.isEnabled
                lastName.isEnabled = !lastName.isEnabled
                gender.isEnabled = !gender.isEnabled
                dob.isEnabled = !dob.isEnabled
                profession.isEnabled = !profession.isEnabled
                cityName.isEnabled = !cityName.isEnabled
            }
        }
        binding.saveInfoButton.setOnClickListener {
            firstname = binding.firstName.text.toString()
            lastname = binding.lastName.text.toString().trim()
            profession = binding.profession.text.toString().trim()
            cityname = binding.cityName.text.toString().trim()
            dob = binding.dob.text.toString().trim()

            updateUserData(firstname, lastname, gender, dob, profession, cityname)
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        binding.backButton.setOnClickListener {
            val intent= Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun updateUserData(
        firstname: String,
        lastname: String,
        gender: String,
        dob: String,
        profession: String,
        cityname: String
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.child("profile").child(userId)
            val userData = hashMapOf(
                "firstName" to firstname,
                "lastName" to lastname,
                "gender" to gender,
                "dob" to dob,
                "profession" to profession,
                "cityName" to cityname
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Profile Update Failed", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    }

    private fun saveUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.child("profile").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(ProfileInfo::class.java)
                        if (userProfile != null) {
                            binding.firstName.setText(userProfile.firstName)
                            binding.lastName.setText(userProfile.lastName)
                            binding.dob.setText(userProfile.dob)
                            binding.profession.setText(userProfile.profession)
                            binding.cityName.setText(userProfile.cityName)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }


    companion object {
    }
}