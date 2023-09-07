package com.thedevelopers.Flatten

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class requestFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val root = inflater.inflate(R.layout.fragment_request, container, false)
        root.findViewById<Button>(R.id.request_submit).setOnClickListener {
            val name = root.findViewById<EditText>(R.id.user_name).text.toString()
            val address = root.findViewById<EditText>(R.id.address).text.toString()
            val mobileNumber = root.findViewById<EditText>(R.id.mobile_number).text.toString()
            val help = root.findViewById<EditText>(R.id.help).text.toString()
            if(name == ""){
                Toast.makeText(context,"Please Enter your Name",Toast.LENGTH_SHORT).show()
            }
            else if(address == ""){
                Toast.makeText(context,"Please Enter your Address",Toast.LENGTH_SHORT).show()
            }
            else if(mobileNumber.length != 10){
                Toast.makeText(context,"Please Enter your Mobile Number Correctly",Toast.LENGTH_SHORT).show()
            }
            else if(help == ""){
                Toast.makeText(context,"Please Enter the help needed",Toast.LENGTH_SHORT).show()
            }
            else{
                val RequestId = System.currentTimeMillis().toString()
                val helpHashMap = hashMapOf("Name" to name
                ,"Mobile" to mobileNumber
                ,"Address" to address
                ,"Help" to help
                ,"Requester" to FirebaseAuth.getInstance().currentUser.uid.toString()
                ,"RequestId" to RequestId)
                FirebaseFirestore.getInstance()
                        .collection("Details")
                        .document(RequestId)
                        .set(helpHashMap).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(context,"Request submitted successfully",Toast.LENGTH_SHORT).show()
                                val name = root.findViewById<EditText>(R.id.user_name).text.clear()
                                val address = root.findViewById<EditText>(R.id.address).text.clear()
                                val mobileNumber = root.findViewById<EditText>(R.id.mobile_number).text.clear()
                                val help = root.findViewById<EditText>(R.id.help).text.clear()
                            }
                            else{
                                Toast.makeText(context,"An error Occurred",Toast.LENGTH_SHORT).show()

                            }
                        }
            }

        }

        return root
    }


}