package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log

class SignupActivity:AppCompatActivity(){
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth= FirebaseAuth.getInstance()

        val email: EditText = findViewById(R.id.ETemail_signup)
        val name: EditText = findViewById(R.id.ETname_signup)
        val password:EditText = findViewById(R.id.ETpassword_signup)
        val con_password :EditText = findViewById(R.id.ETconfirmpassword_signup)
        val signup :TextView = findViewById(R.id.TVsignup_signup)
        name.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val view = this.currentFocus

                val hide= getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
                hide.hideSoftInputFromWindow(view?.windowToken,0)
                return@OnKeyListener true
            }
            false
        })
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
                //hash password and add password and email and name to database
            }
            validateData(name.text.toString().trim(), email.text.toString().trim(), password.text.toString().trim(), con_password.text.toString().trim())


        }

    }
    private fun validateData(name: String, email:String, password:String, con_password:String){
        if(name.isEmpty()){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
        }
        else if(password.length<6){
            Toast.makeText(this, "Password has to be 6 characters or more", Toast.LENGTH_SHORT).show()

        }
        else if(con_password.isEmpty()){
            Toast.makeText(this, "Confirm password", Toast.LENGTH_SHORT).show()
        }
        else if (password!=con_password){
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()

        }
        else {
            createAccount(email, password,name)
        }
    }
    private fun createAccount(email:String, password: String ,name: String ){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
            updateUserInfo(email, password,name)
        }
            .addOnFailureListener{ e->
                Toast.makeText(this, "Failed to create account"+ e.toString(), Toast.LENGTH_SHORT).show()
                println(e)
            }
    }

    private fun updateUserInfo(email: String, password: String, name: String) {
        val timestamp = System.currentTimeMillis()
        val uid = firebaseAuth.uid
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"]= uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Account Created account", Toast.LENGTH_SHORT).show()


                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener{ e->
                Toast.makeText(this, "Failed to save user info due to "+ e.toString(), Toast.LENGTH_SHORT).show()
                println(e)
            }

    }
}