package com.thedevelopers.Flatten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        findViewById<Button>(R.id.reset_password_btn).setOnClickListener {
            val email = findViewById<EditText>(R.id.email_reset).text.toString()
            if(email != ""){
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this@ForgotPasswordActivity,"Reset Email sent to the given Email",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else{
                        Toast.makeText(this@ForgotPasswordActivity,"An error Occurred",Toast.LENGTH_SHORT).show()

                    }
                }
            }
            else{
                Toast
                    .makeText(this@ForgotPasswordActivity,"Please Enter your Registered Email Id",Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}