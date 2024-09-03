package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class signup : AppCompatActivity() {

        private lateinit var googleSignInClient : GoogleSignInClient
   // private lateinit var binding: ActivityProjectadminsigninBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val auth = FirebaseAuth.getInstance()

    private lateinit var btnSignIn: Button
    private lateinit var signupButton:Button
    private lateinit var signupEmailEditText:EditText
    private lateinit var signupConfirmPasswordEditText:EditText
    private lateinit var signupPasswordEditText:EditText
    private lateinit var textView: TextView
    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

         val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        btnSignIn=findViewById(R.id.gSignInBtn)
        signupButton=findViewById(R.id.button)
        textView=findViewById(R.id.textView)
        signupEmailEditText=findViewById(R.id.emailEt)
        signupPasswordEditText=findViewById(R.id.passET)
        signupConfirmPasswordEditText=findViewById(R.id.confirmPassEt)
        btnSignIn.setOnClickListener {
            signIn()
        }
        textView.setOnClickListener {
            val intent = Intent(this, adminlogin::class.java)
            startActivity(intent)
        }

        signupButton.setOnClickListener {
            val email = signupEmailEditText.text.toString()
            val password = signupPasswordEditText.text.toString()
            val confirmPassword = signupConfirmPasswordEditText.text.toString()

            if (password == confirmPassword) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, adminlogin::class.java)
            startActivity(intent)
                            // Redirect to the sign-in activity or perform other actions
                        } else {
                            Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        @Suppress("DEPRECATION")
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Log.e("TAG", "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    Toast.makeText(
                        this,
                        "Signed in successfully: ${user?.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val ExxHomee = Intent(this,viewpage::class.java)
                    startActivity(ExxHomee)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("TAG", "Sign in with Google failed", task.exception)
                    Toast.makeText(this, "Sign in with Google failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

