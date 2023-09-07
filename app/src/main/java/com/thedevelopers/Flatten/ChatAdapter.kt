package com.thedevelopers.Flatten

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: ChatFragment, val items:ArrayList<HashMap<String,String>>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_item_row_chat,parent,false))
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        val item=items.get(position)
        holder.message.text=item["message"]
        holder.sender.text = item["sender"]
        val time = item["time"].toString()
        val month = time.substring(4,6)
        val date = time.substring(6,8)
        val hour = time.substring(8,10)
        val minute = time.substring(10,12)
        holder.timeStamp.text = hour + ":" + minute + "  " + date + "-" + month
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val message:TextView=view.findViewById(R.id.message)
        val cardView: CardView = view.findViewById(R.id.message_card_view)
        val sender: TextView = view.findViewById(R.id.message_sender)
        val timeStamp: TextView = view.findViewById(R.id.time_stamp)
    }
}