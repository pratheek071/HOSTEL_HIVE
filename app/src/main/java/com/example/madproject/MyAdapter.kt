package com.example.madproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
        parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.Name.text = currentitem.Name
        holder.Semester.text = currentitem.Semester
        holder.Branch.text = currentitem.Branch
        holder.Usn.text = currentitem.Usn
        holder.Roomno.text = currentitem.Roomno
        holder.Studentno.text = currentitem.Studentno
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val Name : TextView = itemView.findViewById(R.id.name)
        val Semester : TextView = itemView.findViewById(R.id.semester)
        val Branch : TextView = itemView.findViewById(R.id.branch)
        val Usn : TextView = itemView.findViewById(R.id.usn)
        val Roomno : TextView = itemView.findViewById(R.id.roomno)
        val Studentno : TextView = itemView.findViewById(R.id.studentno)
    }

}

