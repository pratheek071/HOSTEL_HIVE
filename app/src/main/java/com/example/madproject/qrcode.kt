package com.example.madproject

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

class qrcode : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var generateButton: Button
    private lateinit var qrCodeImageView: ImageView
    private lateinit var back:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.textField)
        generateButton = findViewById(R.id.generate)
        qrCodeImageView = findViewById(R.id.qrcode)
        back=findViewById(R.id.back)

        back.setOnClickListener {
            val Intent= Intent(this,viewpage::class.java)
        startActivity(Intent)
        }

        generateButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                generateQRCode()
            }
        })
    }

    private fun generateQRCode() {
        val textToEncode = editText.text.toString()

        try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                textToEncode,
                BarcodeFormat.QR_CODE,
                400, // Width and height of the QR code
                400
            )

            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix[x, y]) resources.getColor(R.color.black) else resources.getColor(R.color.white)
                    )
                }
            }

            qrCodeImageView.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}
