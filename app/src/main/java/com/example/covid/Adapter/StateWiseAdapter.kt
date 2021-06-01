package com.example.covid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.R
import com.example.covid.model.StateWiseModel

class StateWiseAdapter: RecyclerView.Adapter<statewiseViewHolder>() {
   private  val item :ArrayList<StateWiseModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): statewiseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_state_wise,parent,false)
         return statewiseViewHolder(view)
    }

    override fun onBindViewHolder(holder: statewiseViewHolder, position: Int) {
              val currentitem = item[position]
                 holder.state.text = currentitem.state
                 holder.cases.text =currentitem.confirmed
    }

    override fun getItemCount(): Int {
         return item.size
    }

    fun updatestatedata(data:ArrayList<StateWiseModel>){
        item.clear()
        item.addAll(data)
        notifyDataSetChanged()
    }
}

class statewiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val state:TextView = itemView.findViewById(R.id.statename)
    val cases:TextView = itemView.findViewById(R.id.cases)

}