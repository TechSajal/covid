package com.example.covid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.EachStateDataActivity
import com.example.covid.R
import com.example.covid.constant.Constants
import com.example.covid.model.StateWiseModel


class stateWiseAdapter(private var mContext: Context ):RecyclerView.Adapter<StatewiseViewHolder>()  {
    private var item :ArrayList<StateWiseModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatewiseViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_state_wise,parent,false)
         return StatewiseViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatewiseViewHolder, position: Int) {
              val currentitem = item[position]
                 holder.state.text = currentitem.state
                 holder.cases.text =currentitem.confirmed
                 holder.linearlayout.setOnClickListener {
                   //   val currentitem:StateWiseModel = item[position]
                      val intent = Intent(mContext,EachStateDataActivity::class.java)
                     intent.putExtra(Constants().STATE_NAME,currentitem.state)
                     intent.putExtra(Constants().STATE_ACTIVE,currentitem.active)
                     intent.putExtra(Constants().STATE_CONFIRMED,currentitem.confirmed)
                     intent.putExtra(Constants().STATE_CONFIRMED_NEW,currentitem.confirmed_new)
                     intent.putExtra(Constants().STATE_DEATH,currentitem.death)
                     intent.putExtra(Constants().STATE_DEATH_NEW,currentitem.death_new)
                     intent.putExtra(Constants().STATE_LAST_UPDATE,currentitem.lastupdate)
                     intent.putExtra(Constants().STATE_RECOVERED,currentitem.recovered)
                     intent.putExtra(Constants().STATE_RECOVERED_NEW,currentitem.recovered_new)
                     mContext.startActivity(intent)
                 }
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
     val linearlayout:LinearLayout = itemView.findViewById(R.id.layout_state_wise_lin)
}