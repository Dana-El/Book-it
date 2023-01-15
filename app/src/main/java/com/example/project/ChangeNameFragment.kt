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

class ChangeNameFragment : Fragment(R.layout.fragment_change_name) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cancel = view.findViewById<TextView>(R.id.cancel_changename)
        val submit = view.findViewById<TextView>(R.id.submit_changename)
        val name = view.findViewById<EditText>(R.id.new_name_chnagename)
        cancel.setOnClickListener {
            val intent = Intent(requireActivity(),MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        submit.setOnClickListener {
            GroupsFragment().recieveFeedback(name.text.toString())
            val intent = Intent(requireActivity(),MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }




    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}