package com.example.covid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.covid.EachCountryDataActivity
import com.example.covid.R
import com.example.covid.constant.Constants
import com.example.covid.model.CountryWiseModel
import com.example.covid.model.DistrictWiseModel

class CountryWiseAdapter(val mContext:Context):RecyclerView.Adapter<Countrywiseviewholder>() {
     val item :ArrayList<CountryWiseModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Countrywiseviewholder {
          val view = LayoutInflater.from(mContext).inflate(R.layout.layout_country_wise,parent,false)
          return Countrywiseviewholder(view)
    }

    override fun onBindViewHolder(holder: Countrywiseviewholder, position: Int) {
              val data = item[position]
             holder.countryname.text = data.country
             holder.countrytotalcases.text = data.confirmed
            val counrtyrank = (position+1).toString()
            holder.countryrank.text = ("$counrtyrank.")
         Glide.with(mContext).load(data.flag).diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.countryflag)
          holder.linearlayoutcounrty.setOnClickListener {
              val intent = Intent(mContext,EachCountryDataActivity::class.java)
               intent.putExtra(Constants().COUNTRY_NAME,data.country)
              intent.putExtra(Constants().COUNTRY_CONFIRMED,data.confirmed)
              intent.putExtra(Constants().COUNTRY_NEW_CONFIRMED,data.newConfirmed)
              intent.putExtra(Constants().COUNTRY_ACTIVE,data.active)
              intent.putExtra(Constants().COUNTRY_DECEASED,data.deceased)
              intent.putExtra(Constants().COUNTRY_NEW_DECEASED,data.newDeceased)
              intent.putExtra(Constants().COUNTRY_RECOVERED,data.recovered)
              intent.putExtra(Constants().COUNTRY_NEW_RECOVERED,data.recovered_new)
              intent.putExtra(Constants().COUNTRY_TESTS,data.tests)
             mContext.startActivity(intent)
          }

    }

    fun updateDistrictdata(data:ArrayList<CountryWiseModel>){
        item.clear()
        item.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
           return item.size
    }
}

class Countrywiseviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val countryname = itemView.findViewById<TextView>(R.id.layout_country_wise_country_name_textview)!!
    val countryflag:ImageView = itemView.findViewById(R.id.layout_country_wise_flag_imageview)!!
    val countrytotalcases:TextView = itemView.findViewById(R.id.layout_country_wise_confirmed_textview)!!
    val countryrank :TextView = itemView.findViewById(R.id.layout_country_wise_country_rank)!!
    val linearlayoutcounrty:LinearLayout = itemView.findViewById(R.id.layout_country_wise_lin)!!
}