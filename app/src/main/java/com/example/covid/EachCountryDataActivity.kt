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
import org.w3c.dom.Text
import java.text.NumberFormat

class EachCountryDataActivity : AppCompatActivity() {
    private val activity = MainActivity()
    private var confirmed: String? = null
    private  var confirmed_new:String? = null
    private  var active:String? = null
    private var countryname:String? = null
    private  var recovered:String? = null
    private  var recovered_new:String? = null
    private var death: String? = null
    private var tests:String? = null
    private  var death_new:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_country_data)

        val intent:Intent = intent
          confirmed = intent.getStringExtra(Constants().COUNTRY_CONFIRMED)
          confirmed_new = intent.getStringExtra(Constants().COUNTRY_NEW_CONFIRMED)
          active = intent.getStringExtra(Constants().COUNTRY_ACTIVE)
         countryname = intent.getStringExtra(Constants().COUNTRY_NAME)
        recovered = intent.getStringExtra(Constants().COUNTRY_RECOVERED)
        recovered_new = intent.getStringExtra(Constants().COUNTRY_NEW_RECOVERED)
        death = intent.getStringExtra(Constants().COUNTRY_DECEASED)
        death_new = intent.getStringExtra(Constants().COUNTRY_NEW_DECEASED)
        tests = intent.getStringExtra(Constants().COUNTRY_TESTS)
        supportActionBar!!.title = "$countryname"
        fetcheachcountrydata()
    }

    fun fetcheachcountrydata(){
          activity.showdialog(this)
        var conf :TextView = findViewById(R.id.activity_each_country_data_confirmed_textView)
        val conf_new :TextView = findViewById(R.id.activity_each_country_data_confirmed_new_textView)
        val act :TextView = findViewById(R.id.activity_each_country_data_active_textView)
        val act_new :TextView = findViewById(R.id.activity_each_country_data_active_new_textView)
        val dea :TextView = findViewById(R.id.activity_each_country_data_death_textView)
        val dea_new :TextView = findViewById(R.id.activity_each_country_data_death_new_textView)
        val rec :TextView = findViewById(R.id.activity_each_country_data_recovered_textView)
        val rec_new :TextView = findViewById(R.id.activity_each_country_data_recovered_new_textView)
        val tes :TextView = findViewById(R.id.activity_each_country_data_tests_textView)
        val piechart :PieChart = findViewById(R.id.activity_each_country_data_piechart)
        Handler().postDelayed({
              conf.text = NumberFormat.getInstance().format(Integer.parseInt(confirmed))
             conf_new.text = ("+" + NumberFormat.getInstance().format(Integer.parseInt(confirmed_new)))
             act.text = NumberFormat.getInstance().format(Integer.parseInt(active))
            dea.text = NumberFormat.getInstance().format(Integer.parseInt(death))
            dea_new.text = ("+" +NumberFormat.getInstance().format(Integer.parseInt(death_new)))
            rec.text = NumberFormat.getInstance().format(Integer.parseInt(recovered))
            rec_new.text =("+" +NumberFormat.getInstance().format(Integer.parseInt(recovered_new)))
            tes.text = NumberFormat.getInstance().format(Integer.parseInt(tests))
            act_new.text = (Integer.parseInt(confirmed_new) - (Integer.parseInt(death_new) + Integer.parseInt(recovered_new))).toString()
            piechart.addPieSlice(PieModel("Active", Integer.parseInt(active).toFloat(), Color.parseColor("#007afe")))
            piechart.addPieSlice(PieModel("Recovered", Integer.parseInt(recovered).toFloat(), Color.parseColor("#08a045")))
            piechart.addPieSlice(PieModel("Deceased", Integer.parseInt(death).toFloat(), Color.parseColor("#F6404F")))
            piechart.startAnimation()
            activity.DismissDialog()
                              },2000)
    }
}