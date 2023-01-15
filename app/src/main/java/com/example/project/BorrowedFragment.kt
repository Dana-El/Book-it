package com.example.project

import android.content.Context
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

class BorrowedFragment : Fragment(R.layout.fragment_borrowed) {
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view?.findViewById<LinearLayout>(R.id.borrowed_layout)

        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val children = snapshot!!.children
                //val create = snapshot.child("Groups")
                var borrowRef = FirebaseDatabase.getInstance().getReference("Borrows")

                firebaseAuth = FirebaseAuth.getInstance()

                borrowRef.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot!!.children
                        children.forEach { it ->

                            val vv:View = getLayoutInflater().inflate(R.layout.borrowed_book_card,null)
                            val bookname :TextView= vv.findViewById(R.id.borrowed_name)
                            val groupname :TextView= vv.findViewById(R.id.borrowed_author)
                            val ownername :TextView= vv.findViewById(R.id.borrowed_person)
                            var name = it
                            if(it.child("borrower").value == firebaseAuth.getCurrentUser()?.getUid()){
                                bookname.setText( it.child("bookname").value.toString() )
                                val usersRef = rootRef.child("Users")

//                                    println(
//                                    firebaseAuth.getCurrentUser()?.getUid()
//                                        ?.let { it1 -> usersRef.child(it1).child("name").get() }
//                                    )

                                var name :String = ""
                                var rootRef = FirebaseDatabase.getInstance().reference
                                rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val children = snapshot!!.children

                                        var userRef = FirebaseDatabase.getInstance().getReference("Users")

                                        firebaseAuth = FirebaseAuth.getInstance()

                                        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                val children = snapshot!!.children

                                                firebaseAuth = FirebaseAuth.getInstance()
                                                children.forEach {it2->
                                                    firebaseAuth = FirebaseAuth.getInstance()
                                                    val test = it2.child("uid").value
                                                    println(test)
                                                    println(it.child("owner").value)
                                                    println(" ")
                                                    if (it2.child("uid").value.toString() ==it.child("owner").value.toString()){
                                                        name = it2.child("name").value.toString()
                                                        ownername.setText(name)

                                                    }

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
                                println(name)
                                groupname.setText( " By : " +it.child("authorname").value.toString() )

                                layout?.addView(vv)
                            }



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






    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}