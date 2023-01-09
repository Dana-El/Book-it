package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity:AppCompatActivity(){
    private lateinit var firebaseAuth:FirebaseAuth
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
            input_email= email.text.toString().trim()
            var input_password = password.text.toString().trim()
            //hased_password = hashed_passwrd
            //check if email is in database
            //check if passwords match
            // if so go to main activity
            firebaseAuth = FirebaseAuth.getInstance()

            validateData(input_email, input_password)



        }
        signup.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)

        }





    }

    private fun validateData(inputEmail: String, inputPassword: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            Toast.makeText(this, "Invalid email address",Toast.LENGTH_SHORT).show()

        }
        else if(inputPassword.isEmpty()){
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show()
        }
        else {
            loginUser(inputEmail,inputPassword)
        }


    }

    private fun loginUser(inputEmail: String, inputPassword: String) {
        firebaseAuth.signInWithEmailAndPassword(inputEmail,inputPassword)
            .addOnSuccessListener {
                checkUser(inputEmail,inputPassword)
            }
            .addOnFailureListener{
                Toast.makeText(this,"Login failed",Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser(inputEmail: String, inputPassword: String) {
        val firebaseUser= firebaseAuth.currentUser!!
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userType = snapshot.child("userType").value
                    if(userType=="user"){
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        //finish()
                    }
                    else if (userType=="admin"){
                        finish()
                    }

                }
                override fun onCancelled(error: DatabaseError) {


                }
            })

    }

}