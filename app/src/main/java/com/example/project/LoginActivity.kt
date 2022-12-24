package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.internal.ViewUtils.hideKeyboard

class LoginActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        MainActivity.ctr++
        val email :EditText = findViewById(R.id.ETemail)
        val password :EditText =findViewById(R.id.ETpassword)
        val for_password:TextView = findViewById(R.id.TVforgotpassword)
        val login : TextView = findViewById(R.id.TVlogin)
        val signup : TextView = findViewById(R.id.TVsignup)
        var input_email: String
        var hashed_password: String
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

        login.setOnClickListener {
            input_email= email.text.toString()
            //hased_password = hashed_passwrd
            //check if email is in database
            //check if passwords match
            // if so go to main activity
            finish()


        }
        signup.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)

        }





    }
}