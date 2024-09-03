package com.example.madproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private lateinit var button:Button
    private lateinit var secondactbutton:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button=findViewById(R.id.admin_sign)
        secondactbutton =findViewById(R.id.admin_signin)


        secondactbutton.setOnClickListener{
            val Intent= Intent(this,adminlogin::class.java)
            startActivity(Intent)
        }

        button.setOnClickListener{
            val scanner=IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats (IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            val result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
            if(result!=null){
                if(result.contents==null){
                    Toast.makeText(this,"cancelled",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"scanned"+result.contents,Toast.LENGTH_LONG).show()
                    val Intent= Intent(this,phonenumber::class.java)
        startActivity(Intent)
                }
            }
            else{
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}


