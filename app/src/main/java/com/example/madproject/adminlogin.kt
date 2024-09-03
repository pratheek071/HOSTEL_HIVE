package com.example.madproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class adminlogin : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var signinButton:Button
    private lateinit var signinEmailEditText:EditText
    private lateinit var signinPasswordEditText:EditText
    private lateinit var textView: TextView
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)

        signinButton=findViewById(R.id.button)
        signinEmailEditText=findViewById(R.id.emailEt)
        signinPasswordEditText=findViewById(R.id.passET)
        textView=findViewById(R.id.textView)
        text=findViewById(R.id.tv_forgot_password)

        textView.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
        text.setOnClickListener {
            val intent = Intent(this, forgetpasswd::class.java)
            startActivity(intent)
        }

        signinButton.setOnClickListener {
            val email = signinEmailEditText.text.toString()
            val password = signinPasswordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, viewpage::class.java)
            startActivity(intent)
                        // Redirect to another activity or perform other actions
                    } else {
                        Toast.makeText(this, "Sign in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
