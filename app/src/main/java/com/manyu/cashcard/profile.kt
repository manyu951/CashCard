package com.manyu.cashcard

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class profile : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var numberTextView: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //For hideing Action bar
        getSupportActionBar()?.hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference("users")

        // Initialize TextViews
        nameTextView = findViewById(R.id.nameP)
        emailTextView = findViewById(R.id.emailP)
        numberTextView = findViewById(R.id.numberP)

        val button: Button = findViewById(R.id.logout_button)

        button.setOnClickListener {
            logout()
            // For example, you can show a toast message
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    fun logout() {
        mAuth.signOut()
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        startActivity(Intent(this, LoginPage::class.java))
        finish()

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        currentUser?.let { user ->
            val uid = user.uid
            // Query the user details based on UID
            mDatabase.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Fetch user details
                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    val email = dataSnapshot.child("email").getValue(String::class.java)
                    val number = dataSnapshot.child("number").getValue(String::class.java)

                    // Update TextViews with fetched user details
                    nameTextView.text = name
                    emailTextView.text = email
                    numberTextView.text = number
                }
                
                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })
        }
    }
}
