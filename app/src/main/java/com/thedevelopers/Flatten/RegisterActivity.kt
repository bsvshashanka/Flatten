package com.thedevelopers.Flatten

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById<Button>(R.id.register_button).setOnClickListener {
            val name = findViewById<EditText>(R.id.register_name).text.toString()
            val email = findViewById<EditText>(R.id.register_email).text.toString()
            val password = findViewById<EditText>(R.id.register_password).text.toString()
            val contactNumber = findViewById<EditText>(R.id.register_contact).text.toString()
            when {
                email == "" -> {
                    Toast.makeText(this@RegisterActivity,"Please Enter the Email address correctly",Toast.LENGTH_SHORT).show()

                }
                password.length < 6 -> {
                    Toast.makeText(this@RegisterActivity,"Please Enter your password with atleast 6 character",Toast.LENGTH_SHORT).show()

                }
                contactNumber.length != 10 -> {
                    Toast.makeText(this@RegisterActivity,"Please enter correct Mobile Number without '+91' ",Toast.LENGTH_SHORT).show()
                }
                name.equals("") ->{
                    Toast.makeText(this@RegisterActivity,"Please enter your name",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Register(email,password,contactNumber,name)
                }
            }
        }
    }

    private fun Register(email: String, password: String, contactNumber: String, name: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task1 ->
            if(task1.isSuccessful){
                val data = hashMapOf("Name" to name,
                    "Contact-Number" to contactNumber)

                FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().currentUser.uid).set(data).addOnCompleteListener { task2 ->
                    if(task2.isSuccessful){
                        Toast.makeText(this@RegisterActivity,"Registeration Successful",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@RegisterActivity,"Registeration Failed(Database)",Toast.LENGTH_SHORT).show()

                    }
                }
            }
            else{
                Toast.makeText(this@RegisterActivity,"Registeration Failed(AUthentication)",Toast.LENGTH_SHORT).show()

            }
        }
    }
}