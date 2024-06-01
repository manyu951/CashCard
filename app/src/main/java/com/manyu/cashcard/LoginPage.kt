package com.manyu.cashcard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var forgotPasswordButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        auth = FirebaseAuth.getInstance()


        if (auth.currentUser != null) {
            // User is already logged in, redirect to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signupButton)
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginPage, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Authenticate user with email and password
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        Toast.makeText(this@LoginPage, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        // TODO: Add your logic to navigate to the main activity or any other activity upon successful login.
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@LoginPage, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        signUpButton.setOnClickListener {
            // Navigate to the registration activity
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

//        forgotPasswordButton.setOnClickListener {
//            // TODO: Add your logic to handle forgot password functionality here.
//            Toast.makeText(this@LoginPage, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
//        }
    }
}