package com.thedevelopers.Flatten

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore


class DetailFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Declaring the Array of Hashmap
        var detailValues = ArrayList<HashMap<String, String>>()
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_detail, container, false)
        val recyclerView:RecyclerView=root.findViewById(R.id.recyclerViewDetails)
        recyclerView.layoutManager=LinearLayoutManager(context)
        val db = FirebaseFirestore.getInstance()
        db.collection("Details").addSnapshotListener { value, error ->
            if(error == null){
                if(value != null){
                    for(change in value.documentChanges){
                        if(change.type == DocumentChange.Type.ADDED){
                            detailValues.add(change.document.data as HashMap<String, String>)
                            val itemAdapter=ItemAdapter(this,detailValues)
                            recyclerView.adapter = itemAdapter
                        }
                    }
                }
            }
        }
        root.findViewById<ImageButton>(R.id.refresh_detail_button).setOnClickListener {
            db.collection("Details").addSnapshotListener { value, error ->
                var detailValues = ArrayList<HashMap<String,String>>()
                if(error == null){
                    if(value != null){
                        for(change in value.documentChanges){
                            if(change.type == DocumentChange.Type.ADDED){
                                detailValues.add(change.document.data as HashMap<String, String>)
                                val itemAdapter=ItemAdapter(this,detailValues)
                                recyclerView.adapter = itemAdapter
                            }
                        }
                    }
                }
            }
        }



        return root
    }



}