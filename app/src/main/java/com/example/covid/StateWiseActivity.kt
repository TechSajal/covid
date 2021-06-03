package com.example.covid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covid.adapter.stateWiseAdapter
import com.example.covid.model.StateWiseModel
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class StateWiseActivity : AppCompatActivity() {
    private var stateWiseModelArrayList: ArrayList<StateWiseModel> = ArrayList()
    private lateinit var mAdapter: stateWiseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_wise)
        supportActionBar!!.title = "Select State"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val recyclerviewstate:RecyclerView = findViewById(R.id.activity_state_wise_recyclerview)
        recyclerviewstate.layoutManager = LinearLayoutManager(this)
        recyclerviewstate.setHasFixedSize(true)
         FetchStateData()
         mAdapter = stateWiseAdapter()
         recyclerviewstate.adapter =mAdapter
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.activity_state_wise_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            FetchStateData()
            swipeRefreshLayout.isRefreshing = false
        }
        val search: EditText = findViewById(R.id.activity_state_wise_search_editText)
                        search.addTextChangedListener(object :TextWatcher{
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int, ) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int, ) {}
                            override fun afterTextChanged(s: Editable?) {
                                 Filters(s.toString())
                            }

                        })



    }
    private fun Filters(text: String) {
        val filteredList = ArrayList<StateWiseModel>()
        for (item in stateWiseModelArrayList) {
            if (item.state.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }
        mAdapter.updatestatedata(filteredList)
    }

    private fun FetchStateData() {
      val queue = Volley.newRequestQueue(this)
        val url = "https://api.covid19india.org/data.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,{  Response ->
                val statejsonarray = Response.getJSONArray("statewise")
                  stateWiseModelArrayList.clear()
              // val statearraylist:ArrayList<StateWiseModel> = ArrayList()
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
                //     statearraylist.add(statedata)
                     stateWiseModelArrayList.add(statedata)


                }

                Handler().postDelayed({
                    mAdapter.updatestatedata(stateWiseModelArrayList)

                }, 1000)

                 }

            ,{Response ->


            })
             queue.add(jsonObjectRequest)
    }


}