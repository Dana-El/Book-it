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
import com.google.firebase.database.FirebaseDatabase

class NewGroupFragment : Fragment(R.layout.fragment_new_group) {
    private lateinit var firebaseAuth :FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText :EditText= view.findViewById(R.id.group_newGroup)
        val bt :Button = view.findViewById(R.id.createButton)
        firebaseAuth = FirebaseAuth.getInstance()
        bt.setOnClickListener {

            //add to database
            if(editText.text.toString().trim().isEmpty()){
                Toast.makeText(requireActivity(),"Enter Category",Toast.LENGTH_SHORT).show()

            }
            if(!editText.text.toString().trim().isEmpty()){
                val timestamp = System.currentTimeMillis()

                val hashMap= HashMap<String,Any?>()
                hashMap["id"] = "$timestamp"
                hashMap["group"] = editText.text.toString().trim()
                hashMap["creator"] = firebaseAuth.getCurrentUser()?.getUid();
                val ref = FirebaseDatabase.getInstance().getReference("Groups")
                ref.child("$timestamp")
                    .setValue(hashMap)
                    .addOnSuccessListener {
                        Toast.makeText(getContext(),"Added Category",Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener{ e->
                        Toast.makeText(getContext(),"Failed to add group due to "+e.message,Toast.LENGTH_SHORT).show()


                    }
            }



        // add to view in groups fragment
            val intent = Intent(requireActivity(),MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}