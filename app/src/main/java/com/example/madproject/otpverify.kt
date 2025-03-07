package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class otpverify : AppCompatActivity() {

    private lateinit var editTextOTP1: EditText
    private lateinit var editTextOTP2: EditText
    private lateinit var editTextOTP3: EditText
    private lateinit var editTextOTP4: EditText
    private lateinit var editTextOTP5: EditText
    private lateinit var editTextOTP6: EditText
    private lateinit var btnVerifyOTP: Button
    private lateinit var textViewResendOTP: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverify)

        editTextOTP1 = findViewById(R.id.otpEditText1)
        editTextOTP2 = findViewById(R.id.otpEditText2)
        editTextOTP3 = findViewById(R.id.otpEditText3)
        editTextOTP4 = findViewById(R.id.otpEditText4)
        editTextOTP5 = findViewById(R.id.otpEditText5)
        editTextOTP6 = findViewById(R.id.otpEditText6)
        btnVerifyOTP = findViewById(R.id.verifyOTPBtn)
        textViewResendOTP = findViewById(R.id.resendTextView)

        auth = FirebaseAuth.getInstance()

        verificationId = intent.getStringExtra("verificationId") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""

        btnVerifyOTP.setOnClickListener {
            val otp = getEnteredOTP()
            if (otp.length == 6) {
                verifyOTP(otp)
            } else {
                Toast.makeText(this, "Please enter the complete OTP", Toast.LENGTH_SHORT).show()
            }
        }

        textViewResendOTP.setOnClickListener {
            resendOTP()
        }

        startTimer()
    }

    private fun getEnteredOTP(): String {
        return StringBuilder().apply {
            append(editTextOTP1.text.toString().trim())
            append(editTextOTP2.text.toString().trim())
            append(editTextOTP3.text.toString().trim())
            append(editTextOTP4.text.toString().trim())
            append(editTextOTP5.text.toString().trim())
            append(editTextOTP6.text.toString().trim())
        }.toString()
    }

    private fun verifyOTP(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    Toast.makeText(this, "Verification successful. Welcome!", Toast.LENGTH_SHORT).show()
                    moveToHomeActivity()
                } else {
                    Toast.makeText(this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun resendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    Toast.makeText(this@otpverify, "Resend OTP failed. Please try again.", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    this@otpverify.verificationId = verificationId
                    Toast.makeText(this@otpverify, "OTP Resent", Toast.LENGTH_SHORT).show()
                    startTimer()
                }
            }
        )
    }

    private fun moveToHomeActivity() {
        val intent = Intent(this,detaillist::class.java)
        startActivity(intent)
        finish()
    }

    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                textViewResendOTP.text = "Resend OTP in ${seconds}s"
                textViewResendOTP.isEnabled = false
            }

            override fun onFinish() {
                textViewResendOTP.text = "Resend OTP"
                textViewResendOTP.isEnabled = true
            }
        }.start()
    }
}

/*import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class otpverify : AppCompatActivity() {

    private lateinit var editTextOTP1: EditText
    private lateinit var editTextOTP2: EditText
    private lateinit var editTextOTP3: EditText
    private lateinit var editTextOTP4: EditText
    private lateinit var editTextOTP5: EditText
    private lateinit var editTextOTP6: EditText

    private lateinit var btnVerifyOTP: Button
    private lateinit var txtResendOTP: TextView
    private lateinit var timer: CountDownTimer

    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null
    private var timeLeft: Long = 60000 // 60 seconds
    private var phoneNumber: String? = null // Add this variable to store the phone number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverify)

        editTextOTP1 = findViewById(R.id.otpEditText1)
        editTextOTP2 = findViewById(R.id.otpEditText2)
        editTextOTP3 = findViewById(R.id.otpEditText3)
        editTextOTP4 = findViewById(R.id.otpEditText4)
        editTextOTP5 = findViewById(R.id.otpEditText5)
        editTextOTP6 = findViewById(R.id.otpEditText6)
        btnVerifyOTP = findViewById(R.id.verifyOTPBtn)
        txtResendOTP = findViewById(R.id.resendTextView)

        auth = FirebaseAuth.getInstance()

        verificationId = intent.getStringExtra("verificationId")
        phoneNumber = intent.getStringExtra("phoneNumber") // Get the phone number from MainActivity

        btnVerifyOTP.setOnClickListener {
            val otp1 = editTextOTP1.text.toString().trim()
            val otp2 = editTextOTP2.text.toString().trim()
            val otp3 = editTextOTP3.text.toString().trim()
            val otp4 = editTextOTP4.text.toString().trim()
             val otp5 = editTextOTP4.text.toString().trim()
             val otp6 = editTextOTP4.text.toString().trim()

            if (otp1.isNotEmpty() && otp2.isNotEmpty() && otp3.isNotEmpty() && otp4.isNotEmpty() && otp5.isNotEmpty() && otp6.isNotEmpty()) {
                val otp = "$otp1$otp2$otp3$otp4$otp5$otp6"
                verifyOTP(otp)
            } else {
                Toast.makeText(this, "Please enter the complete OTP", Toast.LENGTH_SHORT).show()
            }
        }

        txtResendOTP.setOnClickListener {
            resendOTP()
        }

        startTimer()
    }

    private fun verifyOTP(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Verification successful, move to Home activity
                    val intent = Intent(this, detaillist::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // Handle verification failure
                    Toast.makeText(this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun resendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber!!, // Use the stored phone number for resend
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Not used here, verification is handled in OTPVerificationActivity
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    // Handle verification failure
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, token)
                    this@otpverify.verificationId = verificationId

                    // Reset timer
                    timeLeft = 60000
                    startTimer()
                    Toast.makeText(this@otpverify, "OTP has been resent.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                txtResendOTP.isEnabled = true
                txtResendOTP.text = "Resend OTP"
            }
        }.start()
    }

    private fun updateTimer() {
        val seconds = timeLeft / 1000
        txtResendOTP.text = "Resend OTP in ${seconds}s"
        txtResendOTP.isEnabled = false
    }
}



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
class otpverify : AppCompatActivity() {
     private lateinit var auth: FirebaseAuth
    private lateinit var verifyBtn: Button
    private lateinit var resendTV: TextView
    private lateinit var inputOTP1: EditText
    private lateinit var inputOTP2: EditText
    private lateinit var inputOTP3: EditText
    private lateinit var inputOTP4: EditText
    private lateinit var inputOTP5: EditText
    private lateinit var inputOTP6: EditText
    private lateinit var progressBar: ProgressBar

    private lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverify)
         OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        init()
        progressBar.visibility = View.INVISIBLE
        addTextChangeListener()
        resendOTPTvVisibility()

        resendTV.setOnClickListener {
            resendVerificationCode()
            resendOTPTvVisibility()
        }

        verifyBtn.setOnClickListener {
            //collect otp from all the edit texts
            val typedOTP =
                (inputOTP1.text.toString() + inputOTP2.text.toString() + inputOTP3.text.toString()
                        + inputOTP4.text.toString() + inputOTP5.text.toString() + inputOTP6.text.toString())

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    progressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun resendOTPTvVisibility() {
        inputOTP1.setText("")
        inputOTP2.setText("")
        inputOTP3.setText("")
        inputOTP4.setText("")
        inputOTP5.setText("")
        inputOTP6.setText("")
        resendTV.visibility = View.INVISIBLE
        resendTV.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            resendTV.visibility = View.VISIBLE
            resendTV.isEnabled = true
        }, 60000)
    }

    private fun resendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }
            progressBar.visibility = View.VISIBLE
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            OTP = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(this, "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
                progressBar.visibility = View.VISIBLE
            }
    }

    private fun sendToMain() {
        startActivity(Intent(this, detaillist::class.java))
    }

    private fun addTextChangeListener() {
        inputOTP1.addTextChangedListener(EditTextWatcher(inputOTP1))
        inputOTP2.addTextChangedListener(EditTextWatcher(inputOTP2))
        inputOTP3.addTextChangedListener(EditTextWatcher(inputOTP3))
        inputOTP4.addTextChangedListener(EditTextWatcher(inputOTP4))
        inputOTP5.addTextChangedListener(EditTextWatcher(inputOTP5))
        inputOTP6.addTextChangedListener(EditTextWatcher(inputOTP6))
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.otpprogressBar)
        verifyBtn = findViewById(R.id.verifyOTPBtn)
        resendTV = findViewById(R.id.resendTextView)
        inputOTP1 = findViewById(R.id.otpEditText1)
        inputOTP2 = findViewById(R.id.otpEditText2)
        inputOTP3 = findViewById(R.id.otpEditText3)
        inputOTP4 = findViewById(R.id.otpEditText4)
        inputOTP5 = findViewById(R.id.otpEditText5)
        inputOTP6 = findViewById(R.id.otpEditText6)
    }


    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {

            val text = p0.toString()
            when (view.id) {
                R.id.otpEditText1 -> if (text.length == 1) inputOTP2.requestFocus()
                R.id.otpEditText2 -> if (text.length == 1) inputOTP3.requestFocus() else if (text.isEmpty()) inputOTP1.requestFocus()
                R.id.otpEditText3 -> if (text.length == 1) inputOTP4.requestFocus() else if (text.isEmpty()) inputOTP2.requestFocus()
                R.id.otpEditText4 -> if (text.length == 1) inputOTP5.requestFocus() else if (text.isEmpty()) inputOTP3.requestFocus()
                R.id.otpEditText5 -> if (text.length == 1) inputOTP6.requestFocus() else if (text.isEmpty()) inputOTP4.requestFocus()
                R.id.otpEditText6 -> if (text.isEmpty()) inputOTP5.requestFocus()

            }
        }

    }
}*/