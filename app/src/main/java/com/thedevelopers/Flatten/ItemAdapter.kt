package com.thedevelopers.Flatten

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ItemAdapter(val context: DetailFragment, val items:ArrayList<HashMap<String, String>>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_item_row_details,parent,false))
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item=items.get(position)
        val MobileNumber = item["Mobile"]
        holder.name.text=item["Name"]
        holder.mobile.text= MobileNumber
        holder.address.text=item["Address"]
        holder.help.text=item["Help"]
        val requestId = item["RequestId"].toString()
        val requester = item["Requester"]
        holder.requestCloseButton.setOnClickListener (object : View.OnClickListener{
            override fun onClick(view: View?) {
                val builder = AlertDialog.Builder(view?.context)
                builder.setTitle("Flatten")
                builder.setMessage("Are you sure to Delete the request")
                builder.setPositiveButton("Yes"){ dialog, which ->
                    val currentUid = FirebaseAuth.getInstance().currentUser.uid.toString()
                    if(requester == currentUid){
                        FirebaseFirestore.getInstance()
                                .collection("Details")
                                .document(requestId)
                                .delete()
                                .addOnSuccessListener {  Toast
                                        .makeText(view?.context,"The changes will be seen after you refresh or restart the app",Toast.LENGTH_SHORT)
                                        .show()}
                    }
                    else{
                        Toast.makeText(view?.context,"You can only close requests which are made by you",Toast.LENGTH_SHORT).show()
                    }
                }
                builder.setNegativeButton("No"){dialog, which ->

                }
                builder.show()
            }
        })
        holder.callButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                val intent = Intent(Intent.ACTION_DIAL);
                intent.data = Uri.parse("tel: $MobileNumber");
                if (view != null) {
                    startActivity(view.context, Intent.createChooser(intent,"Dial"), Bundle.EMPTY)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val name: TextView=view.findViewById(R.id.name_item)
        val mobile:TextView=view.findViewById(R.id.mobile_item)
        val address:TextView=view.findViewById(R.id.address_item)
        val help:TextView=view.findViewById(R.id.help_item)
        val cardViewItem: CardView =view.findViewById(R.id.card_view_item)
        val requestCloseButton = view.findViewById<ImageButton>(R.id.closeButton)
        val callButton: ImageButton = view.findViewById(R.id.callButton)
    }
}