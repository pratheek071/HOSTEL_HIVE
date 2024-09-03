package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class permissiongranted : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissiongranted)

        val secondact1button = findViewById<Button>(R.id.view)
        secondact1button.setOnClickListener{
            val Intent= Intent(this,MainActivity::class.java)
        startActivity(Intent)
    }
    }
}