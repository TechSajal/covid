package com.example.covid

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.covid.adapter.CountryWiseAdapter
import com.example.covid.model.CountryWiseModel
import org.json.JSONObject
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class CountryWiseDataActivity : AppCompatActivity() {

    private lateinit var mAdapter :CountryWiseAdapter
    private var countryWiseModelArrayList: ArrayList<CountryWiseModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_wise_data)
        val recyclerView :RecyclerView =findViewById(R.id.activity_country_wise_recyclerview)
         recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        mAdapter = CountryWiseAdapter(this)
        recyclerView.adapter = mAdapter
        fetchcountrydata()

    }

    private fun fetchcountrydata() {
        val url = "https://corona.lmao.ninja/v2/countries"
        val queue = Volley.newRequestQueue(this)
        countryWiseModelArrayList.clear()
        val jsonArrayRequest  = JsonArrayRequest(Request.Method.GET,url,null,{responce ->
            for (i in 0 until responce.length()) {
                val jsonObject: JSONObject = responce.getJSONObject(i)
                val jsonObjectFLAG: JSONObject = jsonObject.getJSONObject("countryInfo")
                val countrydata = CountryWiseModel(
                    jsonObject.getString("country"),
                    jsonObject.getString("cases"),
                    jsonObject.getString("todayCases"),
                    jsonObject.getString("active"),
                    jsonObject.getString("deaths"),
                    jsonObject.getString("todayDeaths"),
                    jsonObject.getString("recovered"),
                    jsonObject.getString("todayRecovered"),
                    jsonObject.getString("tests"),
                    jsonObjectFLAG.getString("flag")
                )
                countryWiseModelArrayList.add(countrydata)
             }
            countryWiseModelArrayList.sortWith { o1, o2 ->
                if (o1!!.confirmed.toInt() > o2!!.confirmed.toInt()) {
                    -1
                } else {
                    1
                }
            }
            Handler().postDelayed({
                                       mAdapter.updateDistrictdata(countryWiseModelArrayList)
                 },1000)





        },{Responce ->



        })

        queue.add(jsonArrayRequest)

    }
}