package com.example.covid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.covid.adapter.DistrictWiseAdapter
import com.example.covid.model.DistrictWiseModel
import org.json.JSONObject
import java.util.*

class DistrictWiseDataActivity : AppCompatActivity() {
    private var districtWiseModelArrayList: ArrayList<DistrictWiseModel> = ArrayList()
       private var str_state_name:String? = null
    private lateinit var mAdapter: DistrictWiseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district_wise_data)
          val recyclerviewdistrict:RecyclerView = findViewById(R.id.activity_district_wise_recyclerview)
        recyclerviewdistrict.layoutManager =LinearLayoutManager(this)
           recyclerviewdistrict.setHasFixedSize(true)
        fetchDistrictData()
        mAdapter = DistrictWiseAdapter(this)
        recyclerviewdistrict.adapter = mAdapter
        val intent:Intent = intent
           str_state_name = intent.getStringExtra("statename")
    }

    private fun fetchDistrictData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.covid19india.org/v2/state_district_wise.json"
        val jsonArrayRequest = JsonArrayRequest(
                 Request.Method.GET,url,null, { responce ->
                                    districtWiseModelArrayList.clear()
                          for (i in 1 until responce.length()){
                             val jsonobjectState = responce.getJSONObject(i)
                              if (str_state_name!!.lowercase().equals(jsonobjectState.getString("state").lowercase(Locale.getDefault()))){
                                       val jsonArrayDistrict  = jsonobjectState.getJSONArray("districtData")
                                         for (j in 0 until jsonArrayDistrict.length()){
                                             val jsonobjectDistrict:JSONObject = jsonArrayDistrict.getJSONObject(j)
                                             val jsonobjectDistrictNew:JSONObject = jsonobjectDistrict.getJSONObject("delta")
                                             val districtdata = DistrictWiseModel(
                                                 jsonobjectDistrict.getString("district").toString(),
                                                 jsonobjectDistrict.getString("confirmed").toString(),
                                                 jsonobjectDistrict.getString("active").toString(),
                                                 jsonobjectDistrict.getString("recovered").toString(),
                                                 jsonobjectDistrict.getString("deceased").toString(),
                                                 jsonobjectDistrictNew.getString("confirmed").toString(),
                                                 jsonobjectDistrictNew.getString("recovered").toString(),
                                                 jsonobjectDistrictNew.getString("deceased").toString()
                                             )
                                                districtWiseModelArrayList.add(districtdata)
                                         }
                              }
                      }

                                               Handler().postDelayed({
                                                   mAdapter.updateDistrictdata(districtWiseModelArrayList)
                                               },1000)



            },{response ->

            })
        queue.add(jsonArrayRequest)
    }
}