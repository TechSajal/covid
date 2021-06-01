package com.example.covid

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    private var confirmed: String? = null
    private  var confirmed_new:String? = null
    private  var active:String? = null
    private  var active_new:String? = null
    private  var recovered:String? = null
    private  var recovered_new:String? = null
    private var death: String? = null
    private  var death_new:String? = null
    private  var tests:String? = null
    private  var tests_new:String? = null
    private  var time:String? = null
    private lateinit var con:TextView
    private lateinit var connew:TextView
    private lateinit var act:TextView
    private lateinit var actnew:TextView
    private lateinit var rec:TextView
    private lateinit var recnew:TextView
    private lateinit var dea:TextView
    private lateinit var deanew:TextView
    private lateinit var tes:TextView
    private lateinit var tesnew:TextView
    private lateinit var date:TextView
    private lateinit var tim:TextView
    private lateinit var pieChart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Covid-19 Tracker (India)"
          FetchData()
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
                  FetchData()
            swipeRefreshLayout.isRefreshing = false
        }

          val worlddatabutton = findViewById<LinearLayout>(R.id.activity_main_world_data_lin)
           worlddatabutton.setOnClickListener {
               val intent = Intent(this,WorldDataActivity::class.java)
               startActivity(intent)
               finish()
           }
        val statewise:LinearLayout = findViewById(R.id.statewise)
          statewise.setOnClickListener {
              val intents = Intent(this,StateWiseActivity::class.java)
              startActivity(intents)

          }
    }
        fun FetchData() {
            val queue = Volley.newRequestQueue(this)
            val url = "https://api.covid19india.org/data.json"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    val allstatejsonArray: JSONArray = response.getJSONArray("statewise")
                    val testdatasome: JSONArray = response.getJSONArray("tested")
                    val data_india: JSONObject = allstatejsonArray.getJSONObject(0)
                    val test_data_india: JSONObject = testdatasome.getJSONObject(testdatasome.length() - 1)
                    confirmed = data_india.getString("confirmed")
                    confirmed_new = data_india.getString("deltaconfirmed")
                    active = data_india.getString("active")
                    recovered = data_india.getString("recovered")
                    recovered_new = data_india.getString("deltarecovered")
                    death = data_india.getString("deaths")
                    death_new = data_india.getString("deltadeaths")
                    time = data_india.getString("lastupdatedtime")
                    tests = test_data_india.getString("totalsamplestested")
                    tests_new = test_data_india.getString("samplereportedtoday")
                    active_new =
                        (Integer.parseInt(confirmed_new) - (Integer.parseInt(death_new) + Integer.parseInt(
                            recovered_new))).toString()
                    val delayToshowProcess = Handler()
                    delayToshowProcess.postDelayed({
                        con = findViewById(R.id.confirm)
                        connew = findViewById(R.id.confirmednew)
                        rec = findViewById(R.id.recovered)
                        recnew = findViewById(R.id.recoverednew)
                        act = findViewById(R.id.active)
                        actnew = findViewById(R.id.activenew)
                        dea = findViewById(R.id.death)
                        deanew = findViewById(R.id.deathnew)
                        tes = findViewById(R.id.sampletested)
                        tesnew = findViewById(R.id.sampletestednew)
                        con.text = NumberFormat.getInstance().format(Integer.parseInt(confirmed))
                        connew.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(confirmed_new)))
                        rec.text = NumberFormat.getInstance().format(Integer.parseInt(recovered))
                        recnew.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(recovered_new)))
                        dea.text = NumberFormat.getInstance().format(Integer.parseInt(death))
                        deanew.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(death_new)))
                        tes.text = NumberFormat.getInstance().format(Integer.parseInt(tests))
                        tesnew.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(tests_new)))
                        act.text = NumberFormat.getInstance().format(Integer.parseInt(active))
                        actnew.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(active_new)))
                        date = findViewById(R.id.updateddate)
                        tim = findViewById(R.id.updatedtime)
                        date.text = Formatdate(time.toString(), 1)
                        tim.text = Formatdate(time.toString(), 2)
                        pieChart = findViewById(R.id.activity_main_piechart)
                        pieChart.addPieSlice(PieModel("Active", Integer.parseInt(active).toFloat(), Color.parseColor("#007afe")))
                        pieChart.addPieSlice(PieModel("Recovered", Integer.parseInt(recovered).toFloat(), Color.parseColor("#08a045")))
                        pieChart.addPieSlice(PieModel("Deceased", Integer.parseInt(death).toFloat(), Color.parseColor("#F6404F")))
                        pieChart.startAnimation()
                                                   }, 1000)

                },
                { response ->

                })
            queue.add(jsonObjectRequest)
        }

      @SuppressLint("SimpleDateFormat")
         fun Formatdate(date:String, testcase:Int): String    {
          val dateformat:String
          val mdate =  SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date)
          if (testcase == 1){
                dateformat = SimpleDateFormat("dd.MM.yyyy").format(mdate)
                return dateformat
          }
          else{
                 dateformat = SimpleDateFormat("hh:mm a").format(mdate)
              return dateformat
          }
      }

}