package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class GroupPageFragment : Fragment(R.layout.fragment_group_page) {
    private lateinit var firebaseAuth:FirebaseAuth
    //private lateinit var newname:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view?.findViewById<GridLayout>(R.id.fragment_groupslayout)
        val dataRef=FirebaseDatabase.getInstance().reference.child("data")

        val newbook:View = getLayoutInflater().inflate(R.layout.new_bookcard,null)
        // var remove_group = view.findViewById<TextView>(R.id.groupcard_remove)

        //newname=""
        //var change_name = view.findViewById<TextView>(R.id.groupcard_updatename)

        //val vv: View? = view?.findViewById(R.id.groupcard)
        val currentGroup = getArguments()?.getString("message");
        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val children = snapshot!!.children
                //val create = snapshot.child("Groups")
                var bookRef = FirebaseDatabase.getInstance().getReference("Books")
                var cancel = view.findViewById<TextView>(R.id.grouppage_cancel)
                cancel.setOnClickListener {
                    val intent = Intent(requireActivity(),MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                val data2 :String?= "group"
                firebaseAuth = FirebaseAuth.getInstance()

                bookRef.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot!!.children
                        children.forEach { it ->

                            val test = it.child("group").value.toString()
                            println(currentGroup)

                            if(test==currentGroup){
                                //add to view
                                val vv:View = getLayoutInflater().inflate(R.layout.bookcard,null)
                                val bookname :TextView= vv.findViewById(R.id.tv_bookname)
                                val authorname :TextView= vv.findViewById(R.id.tv_authorname)
                                val name = it
                                bookname.setText( it.child("bookname").value.toString() )
                                authorname.setText( it.child("authorname").value.toString() )
                                layout?.addView(vv)
                                var borrow = view?.findViewById<TextView>(R.id.book_borrow)
                                var owner = firebaseAuth.getCurrentUser()?.getUid()
                                borrow = vv.findViewById<TextView>(R.id.book_borrow)
                                borrow.setOnClickListener() {
                                    val timestamp = System.currentTimeMillis()
                                    val hashMap= HashMap<String,Any?>()
                                    hashMap["id"] = "$timestamp"
                                    val usersRef = rootRef.child("Users")

//                                    println(
//                                    firebaseAuth.getCurrentUser()?.getUid()
//                                        ?.let { it1 -> usersRef.child(it1).child("name") }
//                                    )
                                    //usersRef.child(uid).child("name")
                                    hashMap["borrower"] = firebaseAuth.getCurrentUser()?.getUid()
                                    hashMap["owner"] =owner
                                    hashMap["bookname"] = bookname.text.toString()
                                    hashMap["authorname"]= authorname.text.toString()
                                    val ref = FirebaseDatabase.getInstance().getReference("Borrows")
                                    ref.child("$timestamp")
                                        .setValue(hashMap)
                                        .addOnSuccessListener {
                                            Toast.makeText(getContext(),"Added Borrow",Toast.LENGTH_SHORT).show()

                                        }
                                        .addOnFailureListener{ e->
                                            Toast.makeText(getContext(),"Failed to add borrow due to "+e.message,Toast.LENGTH_SHORT).show()


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

        //add all groups and new group card
        //layout?.addView(vv)
        //layout?.addView(vvv)
        //layout?.addView(vvvv)
        layout?.addView(newbook)

        newbook.setOnClickListener(){
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                val bundle = Bundle()
                bundle.putString("message", currentGroup) // Put anything what you want


                val fragment2 = NewBookFragment()
                fragment2.setArguments(bundle)
                val f :View? = view?.findViewById(R.id.mainConatiner)
                replace(R.id.mainConatiner,fragment2)
                commit()
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}