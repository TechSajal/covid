package com.example.covid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.R
import com.example.covid.model.DistrictWiseModel

class DistrictWiseAdapter(private val mContext:Context): RecyclerView.Adapter<DistrictWiseViewHolder>() {
  private var item:ArrayList<DistrictWiseModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictWiseViewHolder {
         val view = LayoutInflater.from(mContext).inflate(R.layout.layout_district_wise,parent,false)
         return DistrictWiseViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistrictWiseViewHolder, position: Int) {
         val currentitem = item[position]
          holder.district.text =currentitem.district
          holder.cases.text =currentitem.confirmed
    }

    override fun getItemCount(): Int {
       return item.size
    }

    fun updateDistrictdata(data:ArrayList<DistrictWiseModel>){
        item.clear()
        item.addAll(data)
        notifyDataSetChanged()
    }
}




class DistrictWiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val district: TextView = itemView.findViewById(R.id.districtname)
    val cases: TextView = itemView.findViewById(R.id.districtcases)
}