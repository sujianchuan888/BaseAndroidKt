package com.example.myapplication.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity :AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.itemIconTintList = null
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navView, navController)
//        if (intent.getIntExtra("type", 0) == 0) {
//            navView.selectedItemId = R.id.navigation_mine
//        }
    }
}