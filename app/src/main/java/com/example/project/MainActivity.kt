package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    private lateinit var firebaseAuth: FirebaseAuth
    companion object{
        var ctr :Int = 0;
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val bt :Button = findViewById(R.id.bt)
        val intent = Intent(this,LoginActivity::class.java)

        if(ctr==0)
            startActivity(intent)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainConatiner) as NavHostFragment
        navController= navHostFragment.navController
        val navView = findViewById<BottomNavigationView>(R.id.bottomNav)
        setupWithNavController(navView,navController)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.logout,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        firebaseAuth=FirebaseAuth.getInstance()
        when(item.itemId){

            R.id.item_logout -> {
                 Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show()
            //logout
                firebaseAuth.signOut()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}