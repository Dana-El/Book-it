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

class NewBookFragment : Fragment(R.layout.fragment_new_book) {
    private lateinit var firebaseAuth :FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookname :EditText= view.findViewById(R.id.book_newBook)
        val authorname :EditText= view.findViewById(R.id.author_newBook)
        val bt :Button = view.findViewById(R.id.book_createButton)
        firebaseAuth = FirebaseAuth.getInstance()
        bt.setOnClickListener {

            //add to database
            if(bookname.text.toString().trim().isEmpty()){
                Toast.makeText(requireActivity(),"Enter Book Name",Toast.LENGTH_SHORT).show()

            }
            if(authorname.text.toString().trim().isEmpty()){
                Toast.makeText(requireActivity(),"Enter Author Name",Toast.LENGTH_SHORT).show()

            }
            if(!bookname.text.toString().trim().isEmpty() && !authorname.text.toString().trim().isEmpty() ){
                val timestamp = System.currentTimeMillis()

                val hashMap= HashMap<String,Any?>()
                hashMap["id"] = "$timestamp"
                hashMap["group"] = getArguments()?.getString("message");
                hashMap["creator"] = firebaseAuth.getCurrentUser()?.getUid()
                hashMap["bookname"] = bookname.text.toString()
                hashMap["authorname"] = authorname.text.toString()
                val ref = FirebaseDatabase.getInstance().getReference("Books")
                ref.child("$timestamp")
                    .setValue(hashMap)
                    .addOnSuccessListener {
                        Toast.makeText(getContext(),"Added Book",Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener{ e->
                        Toast.makeText(getContext(),"Failed to add book due to "+e.message,Toast.LENGTH_SHORT).show()


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