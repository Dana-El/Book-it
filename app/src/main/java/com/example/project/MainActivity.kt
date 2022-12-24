package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
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

}