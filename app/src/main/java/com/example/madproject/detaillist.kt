package com.example.madproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class detaillist : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var semesterEditText: EditText
    private lateinit var roomNoEditText: EditText
    private lateinit var branchEditText: EditText
    private lateinit var usnEditText: EditText
    private lateinit var studentNoEditText: EditText
    private lateinit var lrdate:EditText
    private lateinit var saveButton: Button
    private lateinit var back:Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaillist) // Set your layout XML here

        // Initialize UI elements
        back=findViewById(R.id.back)
        nameEditText = findViewById(R.id.name)
        semesterEditText = findViewById(R.id.semester)
        roomNoEditText = findViewById(R.id.roomno)
        branchEditText = findViewById(R.id.branch)
        usnEditText = findViewById(R.id.usn)
        studentNoEditText = findViewById(R.id.studentno)
        lrdate=findViewById(R.id.lrwdate)
        saveButton = findViewById(R.id.next)


        dbRef = Firebase.database.reference

        saveButton.setOnClickListener {
            saveEmployeeDat()



        }
        back.setOnClickListener {
            val i =Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }


        private fun saveEmployeeDat() {
            //getting values
            val Name = nameEditText.text.toString()
            val Semester = semesterEditText.text.toString()
            val Roomno = roomNoEditText.text.toString()
            val Branch = branchEditText.text.toString()
            val Usn = usnEditText.text.toString()
            val Studentno = studentNoEditText.text.toString()

            if (Name.isEmpty()) {
                nameEditText.error = "Please enter name"
            }
            if (Semester.isEmpty()) {
                semesterEditText.error = "Please enter age"
            }
            if (Roomno.isEmpty()) {
                roomNoEditText.error = "Please enter salary"
            }

            if (Usn.isEmpty()) {
                usnEditText.error = "Please enter salary"
            }
            if (Studentno.isEmpty()) {
                studentNoEditText.error = "Please enter salary"
            }



            val employee = User( Name, Semester, Roomno, Usn,Branch, Studentno)

            dbRef.child("Users").child(Name).setValue(employee).addOnSuccessListener {
                val i =Intent(this,detaillist2::class.java)
                startActivity(i)

            }.addOnFailureListener{
                                Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()

            }
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    nameEditText.text.clear()
                    semesterEditText.text.clear()
                    roomNoEditText.text.clear()
                    usnEditText.text.clear()
                    branchEditText.text.clear()
                    studentNoEditText.text.clear()



                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }


        }
    }


