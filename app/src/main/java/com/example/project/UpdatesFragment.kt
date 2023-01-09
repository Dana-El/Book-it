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

class UpdatesFragment : Fragment(R.layout.fragment_updates) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layout2 = view?.findViewById<LinearLayout>(R.id.fragment_updateslayout)
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