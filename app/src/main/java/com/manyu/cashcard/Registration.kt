package com.manyu.cashcard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        auth = FirebaseAuth.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        nameEditText = findViewById(R.id.Name)
        emailEditText = findViewById(R.id.Email)
        numberEditText = findViewById(R.id.Number)
        passwordEditText = findViewById(R.id.Password)
        confirmPasswordEditText = findViewById(R.id.ConfirmPassword)
        saveButton = findViewById(R.id.registerButton)

        saveButton.setOnClickListener {
            saveDataToFirebase()
        }
    }

    private fun saveDataToFirebase() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val number = numberEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()


        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || number.isEmpty() || confirmPassword.isEmpty() && password != confirmPassword) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val userId = firebaseAuth.currentUser?.uid ?: ""
                val newUser = User(name,email,number,password)

                databaseReference.child(userId).setValue(newUser)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                        // Open new activity upon successful registration
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                        finish() // Finish current activity to prevent going back to it using back button
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to save data: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }

    }
}
