package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
    public lateinit var firebaseAuth:FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view?.findViewById<GridLayout>(R.id.groupslayout)
        val newgroup:View = getLayoutInflater().inflate(R.layout.new_groupcard,null)
        var remove_group = view.findViewById<TextView>(R.id.groupcard_remove)

        var change_name = view.findViewById<TextView>(R.id.groupcard_updatename)
        //val vv: View? = view?.findViewById(R.id.groupcard)
        var image = view?.findViewById<ImageView>(R.id.group_image)

        val complaint= view?.findViewById<Button>(R.id.complaint)
        if (complaint != null) {
            complaint.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","bookitapp22@gmail.com",null))
                startActivity(Intent.createChooser(emailIntent,"Complaint"))
                Toast.makeText(requireActivity(), "complaint",Toast.LENGTH_SHORT).show()
            }

        }
        //val vv: View? = view?.findViewById(R.id.groupcard)
        //add first three groups
        var rootRef = FirebaseDatabase.getInstance().reference
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
                        firebaseAuth = FirebaseAuth.getInstance()
                        children.forEach {
                            firebaseAuth = FirebaseAuth.getInstance()
                            count++
                            val test = it.child("creator").value
                            if (FirebaseAuth.getInstance().currentUser != null && test == FirebaseAuth.getInstance().currentUser!!.uid) {
                                //add to view
                                val name = it
                                val vv: View =
                                    getLayoutInflater().inflate(R.layout.groupcard, null)
                                val groupname: TextView = vv.findViewById(R.id.groupcard_et)
                                groupname.setText(it.child("group").value.toString())
                                layout?.addView(vv)
                                remove_group = vv.findViewById(R.id.groupcard_remove)
                                remove_group.setOnClickListener() {
                                    layout?.removeView(vv)
                                    name.ref.removeValue()

                                }
                                image = vv.findViewById(R.id.group_image)
                                image?.setOnClickListener{
                                    val bundle = Bundle()
                                    bundle.putString("message", groupname.text.toString()) // Put anything what you want

                                    val fragment2 = GroupPageFragment()
                                    fragment2.setArguments(bundle)
                                    val f :View? = view?.findViewById(R.id.mainConatiner)
                                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.mainConatiner, fragment2)
                                        commit()
                                    }

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


        rootRef = FirebaseDatabase.getInstance().reference
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
                            layout2?.addView(vv)


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