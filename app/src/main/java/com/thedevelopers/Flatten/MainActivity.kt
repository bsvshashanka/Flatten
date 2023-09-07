
package com.thedevelopers.Flatten

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

    class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Network Check
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected == false){
            Toast.makeText(this@MainActivity,"No network Connection", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.signout_test).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }
        val bottomNavigationView: BottomNavigationView=findViewById(R.id.bottomNavigationView)
        val navController =findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
        fun onBackPressed(){
            System.exit(-1)
        }
    }

    }