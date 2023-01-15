package com.example.project

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject
import kotlin.reflect.typeOf


class GroupsFragment : Fragment(R.layout.fragment_groups) {
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var newname:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view?.findViewById<GridLayout>(R.id.fragment_groupslayout)
        val dataRef=FirebaseDatabase.getInstance().reference.child("data")

        val newgroup:View = getLayoutInflater().inflate(R.layout.new_groupcard,null)
        var remove_group = view.findViewById<TextView>(R.id.groupcard_remove)

        newname=""
        var change_name = view.findViewById<TextView>(R.id.groupcard_updatename)
        //val vv: View? = view?.findViewById(R.id.groupcard)
        var image = view?.findViewById<ImageView>(R.id.group_image)

        //when u click on image it opens group page
        //intent.putExtra("message", message);
        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val children = snapshot!!.children
                //val create = snapshot.child("Groups")
                var groupRef = FirebaseDatabase.getInstance().getReference("Groups")

                val data2 :String?= "creator"
                firebaseAuth = FirebaseAuth.getInstance()

                groupRef.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot!!.children
                        children.forEach { it ->

                            val test = it.child("creator").value
                            if(test==firebaseAuth.currentUser!!.uid){
                                //add to view
                                val vv:View = getLayoutInflater().inflate(R.layout.groupcard,null)
                                val groupname :TextView= vv.findViewById(R.id.groupcard_et)
                                val name = it
                                groupname.setText( it.child("group").value.toString() )
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
                                change_name = vv.findViewById(R.id.groupcard_updatename)
                                change_name.setOnClickListener {
                                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.mainConatiner,ChangeNameFragment())
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

        //add all groups and new group card
        //layout?.addView(vv)
        //layout?.addView(vvv)
        //layout?.addView(vvvv)
        layout?.addView(newgroup)

        newgroup.setOnClickListener(){
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                val f :View? = view?.findViewById(R.id.mainConatiner)

                replace(R.id.mainConatiner,NewGroupFragment())
                commit()
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    public fun recieveFeedback(feedback:String){
        newname=feedback
        println(newname+"     ")
    }


}