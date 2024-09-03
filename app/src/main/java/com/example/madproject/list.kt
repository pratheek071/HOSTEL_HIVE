package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class list : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<User>
    private lateinit var phone:EditText
    private lateinit var msg: EditText
    private lateinit var send: Button
    private lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        phone = findViewById(R.id.phoneNumber)
        msg = findViewById(R.id.Message)
        send = findViewById(R.id.send1)
        back = findViewById(R.id.back1)
        back.setOnClickListener{
            val Intent= Intent(this,adminlogout::class.java)
        startActivity(Intent)
    }

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Users")

        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = MyAdapter(userArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
send.setOnClickListener {
            val number = phone.text.toString()
            val msg1 = msg.text.toString()
            if (msg.text.isEmpty()) {

                Toast.makeText(this, "please enter a message", Toast.LENGTH_SHORT).show()

            }
            if (TextUtils.isDigitsOnly(number)) {

                val smsmanager: SmsManager = SmsManager.getDefault()

                smsmanager.sendTextMessage(number, null, msg1, null, null)
                Toast.makeText(this, "sms sent", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "please check your number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }

