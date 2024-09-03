package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class viewpage : AppCompatActivity() {
    private lateinit var secondactbutton:Button
    private lateinit var qrcode1:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpage)


        secondactbutton = findViewById(R.id.view)
        qrcode1 = findViewById(R.id.qr)
        secondactbutton.setOnClickListener{
            val Intent= Intent(this,list::class.java)
        startActivity(Intent)
    }
         qrcode1.setOnClickListener{
            val Intent= Intent(this,qrcode::class.java)
        startActivity(Intent)
    }
    }
}
