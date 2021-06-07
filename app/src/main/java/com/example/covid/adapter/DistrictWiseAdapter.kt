package com.example.covid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.EachDistrictDataActivity
import com.example.covid.R
import com.example.covid.constant.Constants
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
         holder.linearlayout.setOnClickListener {
                 val intent = Intent(mContext,EachDistrictDataActivity::class.java)
                    intent.putExtra(Constants().DISTRICT_NAME,currentitem.district)
             intent.putExtra(Constants().DISTRICT_CONFIRMED,currentitem.confirmed)
             intent.putExtra(Constants().DISTRICT_CONFIRMED_NEW,currentitem.newconfirmed)
             intent.putExtra(Constants().DISTRICT_ACTIVE,currentitem.active)
             intent.putExtra(Constants().DISTRICT_DEATH,currentitem.deceased)
             intent.putExtra(Constants().DISTRICT_DEATH_NEW,currentitem.newdeceades)
             intent.putExtra(Constants().DISTRICT_RECOVERED,currentitem.recovered)
             intent.putExtra(Constants().DISTRICT_RECOVERED_NEW,currentitem.newrecovered)
              mContext.startActivity(intent)
         }
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
    val linearlayout:LinearLayout = itemView.findViewById(R.id.layout_district_wise_lin)

}