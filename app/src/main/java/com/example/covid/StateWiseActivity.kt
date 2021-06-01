package com.example.covid

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covid.Adapter.StateWiseAdapter
import com.example.covid.model.StateWiseModel
import org.json.JSONObject
import kotlin.properties.Delegates

 class StateWiseActivity : AppCompatActivity() {
    private lateinit var mAdapter: StateWiseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_wise)
        supportActionBar!!.title = "Select State"
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val recyclerviewstate:RecyclerView = findViewById(R.id.activity_state_wise_recyclerview)
        recyclerviewstate.layoutManager = LinearLayoutManager(this)
        recyclerviewstate.setHasFixedSize(true)
         FetchStateData()
         mAdapter = StateWiseAdapter()
         recyclerviewstate.adapter =mAdapter

    }

    private fun FetchStateData() {
      val queue = Volley.newRequestQueue(this)
        val url = "https://api.covid19india.org/data.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,{  Response ->
                val statejsonarray = Response.getJSONArray("statewise")
                  val statearray = ArrayList<StateWiseModel>()
                for ( i in 1 until statejsonarray.length()) {
                   val statejsonobject:JSONObject = statejsonarray.getJSONObject(i)
                   val statedata = StateWiseModel(
                       statejsonobject.getString("state"),
                       statejsonobject.getString("confirmed"),
                       statejsonobject.getString("deltaconfirmed"),
                       statejsonobject.getString("active"),
                       statejsonobject.getString("deaths"),
                       statejsonobject.getString("deltadeaths"),
                       statejsonobject.getString("recovered"),
                       statejsonobject.getString("deltarecovered"),
                       statejsonobject.getString("lastupdatedtime")
                   )
                    statearray.add(statedata)
                }

                Handler().postDelayed({

                    mAdapter.updatestatedata(statearray)

                }, 1000)

                 }
            ,{Response ->


            })
             queue.add(jsonObjectRequest)
    }
}