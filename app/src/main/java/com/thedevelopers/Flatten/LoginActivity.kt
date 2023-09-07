package com.thedevelopers.Flatten

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected == false){
            Toast.makeText(this@LoginActivity,"No network Connection", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.open_forgot_password).setOnClickListener {
            startActivity(Intent(this@LoginActivity,ForgotPasswordActivity::class.java))
        }
        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
        findViewById<TextView>(R.id.open_register).setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
        findViewById<Button>(R.id.login_btn).setOnClickListener {
            val email = findViewById<EditText>(R.id.email_signin).text.toString()
            val password = findViewById<EditText>(R.id.password_signin).text.toString()
            if(email == ""){
                Toast.makeText(this@LoginActivity,"Please Enter the Email Address",Toast.LENGTH_SHORT).show()
            }
            else if(password == ""){
                Toast.makeText(this@LoginActivity,"Please enter your password",Toast.LENGTH_SHORT).show()
            }
            else{
                login(email,password)
            }
        }
    }

    private fun login(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this@LoginActivity,"Login Successful",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this@LoginActivity,"Login Failed",Toast.LENGTH_SHORT).show()

            }
        }
    }
}