package com.example.madproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class forgetpasswd : AppCompatActivity() {
    private lateinit var btn_submit: Button
    private lateinit var et_forget_email: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpasswd)
        btn_submit=findViewById(R.id.btn_submit)


        btn_submit.setOnClickListener {
            val email: String = et_forget_email.text.toString().trim { it  <=' ' }
            if (email.isEmpty()) {
                Toast.makeText(
                    this@forgetpasswd,
                    "Please enter email address.",
                    Toast.LENGTH_SHORT

                ).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@forgetpasswd,
                                "Email snet successfully to reset ypur password",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    }
            }
        }
    }
}