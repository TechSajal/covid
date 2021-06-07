package com.example.covid

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.covid.constant.Constants
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat

class EachDistrictDataActivity : AppCompatActivity() {
    private var confirmed: String? = null
    private  var confirmed_new:String? = null
    private  var active:String? = null
    private var districtname:String? = null
    private  var recovered:String? = null
    private  var recovered_new:String? = null
    private var death: String? = null
    private  var death_new:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_district_data)
        val intent : Intent = intent
          confirmed =intent.getStringExtra(Constants().DISTRICT_CONFIRMED)
          confirmed_new = intent.getStringExtra(Constants().DISTRICT_CONFIRMED_NEW)
         active = intent.getStringExtra(Constants().DISTRICT_ACTIVE)
         districtname = intent.getStringExtra(Constants().DISTRICT_NAME)
         recovered = intent.getStringExtra(Constants().DISTRICT_RECOVERED)
        recovered_new = intent.getStringExtra(Constants().DISTRICT_RECOVERED_NEW)
         death = intent.getStringExtra(Constants().DISTRICT_DEATH)
         death_new = intent.getStringExtra(Constants().DISTRICT_DEATH_NEW)
          fetcheachdistrictdata()
        supportActionBar!!.title = "$districtname"
    }

    fun fetcheachdistrictdata(){
        val conf:TextView = findViewById(R.id.confirmed_district)
        val conf_new:TextView = findViewById(R.id.confirmed_new_district)
        val act: TextView = findViewById(R.id.active_district)
        val act_new:TextView = findViewById(R.id.active_new_district)
        val rec:TextView = findViewById(R.id.recovered_district)
        val rec_new :TextView = findViewById(R.id.recovered_new_district)
        val dea:TextView = findViewById(R.id.death_district)
        val dea_new :TextView = findViewById(R.id.death_new_district)
        val pieChart : PieChart = findViewById(R.id.activity_each_district_piechart)
        Handler().postDelayed({
              conf.text = NumberFormat.getInstance().format(Integer.parseInt(confirmed))
              conf_new.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(confirmed_new)))
              act.text = NumberFormat.getInstance().format(Integer.parseInt(active))
              rec.text = NumberFormat.getInstance().format(Integer.parseInt(recovered))
             rec_new.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(recovered_new)))
             dea.text = NumberFormat.getInstance().format(Integer.parseInt(death))
            dea_new.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(death_new)))
            act_new.text = (Integer.parseInt(confirmed_new) - (Integer.parseInt(death_new) + Integer.parseInt(recovered_new))).toString()
            pieChart.addPieSlice(PieModel("Active", Integer.parseInt(active).toFloat(), Color.parseColor("#007afe")))
            pieChart.addPieSlice(PieModel("Recovered", Integer.parseInt(recovered).toFloat(), Color.parseColor("#08a045")))
            pieChart.addPieSlice(PieModel("Deceased", Integer.parseInt(death).toFloat(), Color.parseColor("#F6404F")))
            pieChart.startAnimation()
                              },1000)
    }
}