package com.example.covid

import android.content.Intent
import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class WorldDataActivity : AppCompatActivity() {
    private val activity = MainActivity()
    private  lateinit var confirm_world:TextView
    private lateinit var confirm_new_world:TextView
    private  lateinit var active_world:TextView
    private lateinit var active_new_world:TextView
    private  lateinit var recovered_world:TextView
    private lateinit var recovered_new_world:TextView
    private  lateinit var death_world:TextView
    private lateinit var death_new_world:TextView
    private  lateinit var total_tests_world:TextView
     private var conf_world: String? = null
     private var conf_world_new:String? = null
    private  var acti_world:String? = null
    private var acti_world_new:String? = null
    private var rec_world:String?=null
    private var rec_world_new:String?=null
    private var dea_world:String?=null
    private var dea_world_new:String?=null
    private var tottestworld:String? = null
    private lateinit var pieChart: PieChart
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_data)
        supportActionBar!!.title = "Covid-19 Tracker (World)"
         FetchDataWorld()
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.activity_world_data_swipe_refresh_layout)
            swipeRefreshLayout.setOnRefreshListener {
                   FetchDataWorld()
                   swipeRefreshLayout.isRefreshing = false
            }

        val countrydata = findViewById<LinearLayout>(R.id.activity_world_data_countrywise_lin)
        countrydata.setOnClickListener {
            val intent = Intent(this,CountryWiseDataActivity::class.java)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun FetchDataWorld(){
          activity.showdialog(this)
        val queue = Volley.newRequestQueue(this)
        val url = "https://corona.lmao.ninja/v2/all"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                conf_world = response.getString("cases").toString()
                conf_world_new = response.getString("todayCases").toString()
                rec_world= response.getString("recovered").toString()
                rec_world_new = response .getString("todayRecovered").toString()
                dea_world = response.getString("deaths").toString()
                dea_world_new = response.getString("todayDeaths").toString()
                tottestworld = response.getString("tests").toString()
                acti_world = response.getString("active").toString()
                Handler().postDelayed({
                    acti_world_new = (Integer.parseInt(conf_world_new) - (Integer.parseInt(dea_world_new) + Integer.parseInt(rec_world_new))).toString()
                    confirm_world = findViewById(R.id.confirmworld)
                    confirm_world.text = NumberFormat.getInstance().format(Integer.parseInt(conf_world))
                    confirm_new_world = findViewById(R.id.confirmnewworld)
                    confirm_new_world.text =("+"+ NumberFormat.getInstance().format(Integer.parseInt(conf_world_new)))
                    active_world = findViewById(R.id.activeworld)
                    active_world.text = NumberFormat.getInstance().format(Integer.parseInt(acti_world))
                    active_new_world = findViewById(R.id.activenewworld)
                    active_new_world.text = ("+"+NumberFormat.getInstance().format(Integer.parseInt(acti_world_new)))
                    recovered_world = findViewById(R.id.recoveredworld)
                    recovered_world.text = NumberFormat.getInstance().format(Integer.parseInt(rec_world))
                    recovered_new_world = findViewById(R.id.recoverednewworld)
                    recovered_new_world.text = ("+"+NumberFormat.getInstance().format(Integer.parseInt(rec_world_new)))
                    death_world = findViewById(R.id.deathworld)
                    death_world.text = NumberFormat.getInstance().format(Integer.parseInt(dea_world))
                    death_new_world = findViewById(R.id.deathnewworld)
                    death_new_world.text = ("+"+NumberFormat.getInstance().format(Integer.parseInt(dea_world_new)))
                    total_tests_world = findViewById(R.id.totaltestworld)
                     total_tests_world.text = NumberFormat.getInstance().format((tottestworld)!!.toLong())
                    pieChart = findViewById(R.id.activity_world_data_piechart)
                    pieChart.addPieSlice(PieModel("Active", Integer.parseInt(acti_world).toFloat(), Color.parseColor("#007afe")))
                    pieChart.addPieSlice(PieModel("Recovered", Integer.parseInt(rec_world).toFloat(), Color.parseColor("#08a045")))
                    pieChart.addPieSlice(PieModel("Deceased", Integer.parseInt(dea_world).toFloat(), Color.parseColor("#F6404F")))
                    pieChart.startAnimation()
                    activity.DismissDialog()
                },2000)



            },
            { response ->


            })
        queue.add(jsonObjectRequest)
    }

}