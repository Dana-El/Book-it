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

class BorrowedFragment : Fragment(R.layout.fragment_borrowed) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view?.findViewById<LinearLayout>(R.id.borrowed_layout)
        val vv:View = getLayoutInflater().inflate(R.layout.borrowed_book_card,null)
        val vvv:View = getLayoutInflater().inflate(R.layout.borrowed_book_card,null)
        val vvvv:View = getLayoutInflater().inflate(R.layout.borrowed_book_card,null)

        //val vv: View? = view?.findViewById(R.id.groupcard)
        //add all groups and new group card
        layout?.addView(vv)
        layout?.addView(vvv)
        layout?.addView(vvvv)




    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}