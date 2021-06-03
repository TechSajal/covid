package com.example.covid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.R
import com.example.covid.model.StateWiseModel


class stateWiseAdapter:RecyclerView.Adapter<StatewiseViewHolder>()  {
   private var item :ArrayList<StateWiseModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatewiseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_state_wise,parent,false)
         return StatewiseViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatewiseViewHolder, position: Int) {
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

class StatewiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val state:TextView = itemView.findViewById(R.id.statename)
    val cases:TextView = itemView.findViewById(R.id.cases)

}