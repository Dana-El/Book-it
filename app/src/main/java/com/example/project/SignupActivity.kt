package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignupActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val email: EditText = findViewById(R.id.ETemail_signup)
        val password:EditText = findViewById(R.id.ETpassword_signup)
        val con_password :EditText = findViewById(R.id.ETconfirmpassword_signup)
        val signup :TextView = findViewById(R.id.TVsignup_signup)

        email.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val view = this.currentFocus

                val hide= getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
                hide.hideSoftInputFromWindow(view?.windowToken,0)
                return@OnKeyListener true
            }
            false
        })
        password.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val view = this.currentFocus

                val hide= getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
                hide.hideSoftInputFromWindow(view?.windowToken,0)
                return@OnKeyListener true
            }
            false
        })
        con_password.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val view = this.currentFocus

                val hide= getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
                hide.hideSoftInputFromWindow(view?.windowToken,0)
                return@OnKeyListener true
            }
            false
        })
        signup.setOnClickListener {
            if(password.text.toString()== con_password.text.toString()){
                //hash password and add password and email to database
            }

            val intent = Intent(this,MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }




    }
}