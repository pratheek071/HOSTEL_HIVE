package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class detaillist2 : AppCompatActivity() {
    private lateinit var enterreason: EditText
    private lateinit var addressleave: EditText
    private lateinit var date: EditText
    private lateinit var time: EditText
    private lateinit var date2: EditText
    private lateinit var time2: EditText

    //private lateinit var progressBar2: ProgressBar
    //private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaillist2)
        enterreason = findViewById(R.id.enterreason)
        addressleave = findViewById(R.id.addressleave)
        date = findViewById(R.id.date)
        time = findViewById(R.id.time3)
        date2 = findViewById(R.id.date2)
        time2 = findViewById(R.id.time2)


        val secondactbutton = findViewById<Button>(R.id.done)
        secondactbutton.setOnClickListener {
            val Intent = Intent(this, permissiongranted::class.java)
            startActivity(Intent)
        }
        val secondact1button = findViewById<Button>(R.id.view)
        secondact1button.setOnClickListener {
            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)

        }
    }
}
       /* Save2.setOnClickListener{
            addDocument()
        }
        progressBar2.visibility= View.VISIBLE

        Save2.setOnClickListener{
            if(enterreason.text.toString().isEmpty() or addressleave.text.toString().isEmpty() or date.text.toString().isEmpty() or
                time.text.toString().isEmpty() or date2.text.toString().isEmpty() or
                time2.text.toString().isEmpty()){
                Toast.makeText(this@detaillist2,"Enter All Data", Toast.LENGTH_SHORT).show()
            }else{
                val url="https://script.google.com/macros/s/AKfycbzcEpVe3sIpQPVeg3b8UUuKPvphl3pOdaQO6h3ezMedMwQsWwY0EGxFe7Pu80DI8hWZ/exec"
                val stringRequest=object : StringRequest(Method.POST,url,
                Response.Listener {
                    Toast.makeText(this@detaillist2,it.toString(), Toast.LENGTH_SHORT).show()
                    progressBar2.visibility= View.VISIBLE

                },
                Response.ErrorListener {
                    Toast.makeText(this@detaillist2,it.toString(),Toast.LENGTH_SHORT).show()
                    progressBar2.visibility= View.VISIBLE
                }){
                    override fun getParams():MutableMap<String,String>{
                        val params=HashMap<String,String>()
                        params["EnterReason"]=enterreason.text.toString()
                        params["Addressleave"]=addressleave.text.toString()
                        params["Date"]=date.text.toString()
                        params["Time"]=time.text.toString()
                        params["Date2"]=date2.text.toString()
                        params["Time2"]=time2.text.toString()
                        return params
                    }
                }
                val queue= Volley.newRequestQueue(this@detaillist2)
                queue.add(stringRequest)
            }
        }



    }
    private fun addDocument() {
        val data = hashMapOf(
            "addressleave" to addressleave.text,
            "date" to date.text,
            "time" to time.text,
            "date2" to date2.text,
            "time2" to time2.text,

        )

        db.collection("users")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }
    companion object {
        private const val TAG = "detaillist2"
    }
}*/