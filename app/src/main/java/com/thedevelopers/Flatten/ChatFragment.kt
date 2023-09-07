package com.thedevelopers.Flatten

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val chats = ArrayList<HashMap<String,String>>()
        val root =inflater.inflate(R.layout.fragment_chat, container, false)
        val recyclerView: RecyclerView =root.findViewById(R.id.recyclerViewChat)
        recyclerView.layoutManager= LinearLayoutManager(context)
        val chatAdapter=ChatAdapter(this,chats)
        recyclerView.adapter = chatAdapter
        val db = FirebaseFirestore.getInstance()
        //Receiving Messages
        db.collection("Chats").addSnapshotListener { value, error ->
            if(error == null){
                if(value != null){
                    for(change in value.documentChanges){
                        if(change.type == DocumentChange.Type.ADDED){
                            chats.add(change.document.data as HashMap<String, String>)
                            val chatAdapter=ChatAdapter(this,chats)
                            recyclerView.adapter = chatAdapter
                            recyclerView.scrollToPosition((chatAdapter.itemCount)-1)
                        }
                    }
                }
            }
        }
        //Sending Messages
        root.findViewById<ImageButton>(R.id.chat_send_button).setOnClickListener {
            val message = root.findViewById<EditText>(R.id.chat_edit_text).text.toString()
            if(message != null){
                db.collection("Users").document(FirebaseAuth.getInstance().currentUser.uid).get().addOnSuccessListener { document ->
                    if(document != null){
                        val userData = document.data
                        val name = userData?.get("Name")

                        val currentTime = SimpleDateFormat("yyyyMMddHHmmss").format(Date()).toString()
                        val messageData = hashMapOf("sender" to name,
                                "message" to message,
                                "time" to currentTime,
                                    "uid" to FirebaseAuth.getInstance().currentUser.uid)

                        db.collection("Chats").document(Date().time.toString()).set(messageData)
                        root.findViewById<EditText>(R.id.chat_edit_text).text.clear()
                    }
                }
            }
        }
        return root
    }
}