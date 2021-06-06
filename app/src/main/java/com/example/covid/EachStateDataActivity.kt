package com.example.covid

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.covid.constant.Constants
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.SimpleDateFormat
import java.util.*

class EachStateDataActivity : AppCompatActivity() {
    private var confirmed: String? = null
    private  var confirmed_new:String? = null
    private  var active:String? = null
    private var statename:String? = null
    private  var recovered:String? = null
    private  var recovered_new:String? = null
    private var death: String? = null
    private  var death_new:String? = null
    private  var time:String? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_state_data)
        var conf: TextView = findViewById(R.id.statewise_confirmed)
        var conf_new: TextView = findViewById(R.id.statewise_confirmednew)
        var act: TextView = findViewById(R.id.statewise_active)
        var act_new: TextView = findViewById(R.id.statewise_activenew)
        var rec: TextView = findViewById(R.id.statewise_recovered)
        var rec_new: TextView = findViewById(R.id.statewise_recoverednew)
        var dea: TextView = findViewById(R.id.statewise_death)
        var dea_new: TextView = findViewById(R.id.statewise_deathnew)
        var tim: TextView = findViewById(R.id.statewise_lastupdate)
        var pieChart: PieChart
        val intent: Intent = intent
        confirmed = intent.getStringExtra(Constants().STATE_CONFIRMED)
        confirmed_new = intent.getStringExtra(Constants().STATE_CONFIRMED_NEW)
        active = intent.getStringExtra(Constants().STATE_ACTIVE)
        statename = intent.getStringExtra(Constants().STATE_NAME)
        recovered = intent.getStringExtra(Constants().STATE_RECOVERED)
        confirmed_new = intent.getStringExtra(Constants().STATE_CONFIRMED_NEW)
        recovered = intent.getStringExtra(Constants().STATE_RECOVERED)
        recovered_new = intent.getStringExtra(Constants().STATE_RECOVERED_NEW)
        death = intent.getStringExtra(Constants().STATE_DEATH)
        death_new = intent.getStringExtra(Constants().STATE_DEATH_NEW)
        time = intent.getStringExtra(Constants().STATE_LAST_UPDATE)
        val tv_dist = findViewById<TextView>(R.id.activity_each_state_district_data_title)
        tv_dist.text = "District data of $statename"
        supportActionBar!!.title = "$statename"
        val district: LinearLayout = findViewById(R.id.statewise_districtdata)
        district.setOnClickListener {
            val intent = Intent(this, DistrictWiseDataActivity::class.java)
            intent.putExtra("statename", statename)
            startActivity(intent)
        }

        Handler().postDelayed({
            conf.text = NumberFormat.getInstance().format(Integer.parseInt(confirmed))
            conf_new.text =
                ("+" + NumberFormat.getInstance().format(Integer.parseInt(confirmed_new)))
            act.text = NumberFormat.getInstance().format(Integer.parseInt(active))
            rec.text = NumberFormat.getInstance().format(Integer.parseInt(recovered))
            rec_new.text =
                ("+" + NumberFormat.getInstance().format(Integer.parseInt(recovered_new)))
            dea.text = NumberFormat.getInstance().format(Integer.parseInt(death))
            dea_new.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(death_new)))
            val mdate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(time)
            val dateformat = SimpleDateFormat("dd.MM.yyyy HH:mm").format(mdate)
            tim.text = dateformat
            act_new.text =
                (Integer.parseInt(confirmed_new) - (Integer.parseInt(death_new) + Integer.parseInt(
                    recovered_new))).toString()
            pieChart = findViewById(R.id.activity_each_state_piechart)
            pieChart.addPieSlice(PieModel("Active",
                Integer.parseInt(active).toFloat(),
                Color.parseColor("#007afe")))
            pieChart.addPieSlice(PieModel("Recovered",
                Integer.parseInt(recovered).toFloat(),
                Color.parseColor("#08a045")))
            pieChart.addPieSlice(PieModel("Deceased",
                Integer.parseInt(death).toFloat(),
                Color.parseColor("#F6404F")))
            pieChart.startAnimation()
        }, 1000)


    }
}