package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UpdatesFragment : Fragment(R.layout.fragment_updates) {
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layout = view?.findViewById<LinearLayout>(R.id.fragment_updateslayout)
        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val children = snapshot!!.children
                //val create = snapshot.child("Groups")
                var bookRef = FirebaseDatabase.getInstance().getReference("Books")

                firebaseAuth = FirebaseAuth.getInstance()

                bookRef.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot!!.children
                        children.forEach { it ->

                            val vv:View = getLayoutInflater().inflate(R.layout.updatecard,null)
                            val bookname :TextView= vv.findViewById(R.id.update_bookname)
                            val groupname :TextView= vv.findViewById(R.id.update_groupname)

                            val name = it

                            bookname.setText( it.child("bookname").value.toString() )
                            groupname.setText( it.child("group").value.toString() )
                            layout?.addView(vv)


                        }

                    }


                    override fun onCancelled(error: DatabaseError) {
                        println(error!!.message)
                    }
                })


            }

            override fun onCancelled(error: DatabaseError) {
                println("test" + error!!.message)
            }
        })



        //println(layout)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}