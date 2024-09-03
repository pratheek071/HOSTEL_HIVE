package com.example.madproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class adminlogout : AppCompatActivity() {
    private lateinit var signOutButton: Button
    private lateinit var Exit:Button
    private lateinit var back:Button
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogout)

        signOutButton = findViewById(R.id.logout)
        back=findViewById(R.id.back)
        Exit=findViewById(R.id.view)

        // Set a click listener for the sign out button
        signOutButton.setOnClickListener {
            signOut()
        }
        Exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        back.setOnClickListener {
             val intent = Intent(this, viewpage::class.java)
            startActivity(intent)
        }
    }

    private fun signOut() {
        // Sign out the current user
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        // You can add any additional actions after sign out here.
    }
}
