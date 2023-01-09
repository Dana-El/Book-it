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
import androidx.fragment.app.findFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view?.findViewById<GridLayout>(R.id.groupslayout)
        val vv:View = getLayoutInflater().inflate(R.layout.groupcard,null)
        val vvv:View = getLayoutInflater().inflate(R.layout.groupcard,null)
        val vvvv:View = getLayoutInflater().inflate(R.layout.groupcard,null)

        val newgroup:View = getLayoutInflater().inflate(R.layout.new_groupcard,null)
        //val vv: View? = view?.findViewById(R.id.groupcard)
        //add first three groups

        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                val create = snapshot.child("Groups")
                var groupRef = FirebaseDatabase.getInstance().getReference("Groups")

                val data2 :String?= "creator"
                firebaseAuth = FirebaseAuth.getInstance()

                groupRef.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot!!.children
                        var count =0
                        children.forEach {
                            if(count<4) {
                                count++
                                val test = it.child("creator").value
                                if (test == firebaseAuth.currentUser!!.uid) {
                                    //add to view
                                    val vv: View =
                                        getLayoutInflater().inflate(R.layout.groupcard, null)
                                    val groupname: TextView = vv.findViewById(R.id.groupcard_et)
                                    groupname.setText(it.child("group").value.toString())
                                    layout?.addView(vv)
                                }
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
        layout?.addView(newgroup)
        newgroup.setOnClickListener(){
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                val f :View? = view?.findViewById(R.id.mainConatiner)

                replace(R.id.mainConatiner,NewGroupFragment())
                commit()
            }
        }

        val layout2 = view?.findViewById<LinearLayout>(R.id.updateslayout)
        val test:View = getLayoutInflater().inflate(R.layout.updatecard,null)
        val test2:View = getLayoutInflater().inflate(R.layout.updatecard,null)
        val test3:View = getLayoutInflater().inflate(R.layout.updatecard,null)
        layout2?.addView(test)
        layout2?.addView(test2)
        layout2?.addView(test3)
        //println(layout)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}