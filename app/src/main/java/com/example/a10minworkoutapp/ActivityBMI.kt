package com.example.a10minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a10minworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class ActivityBMI : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        makeVisibleMetricUnitView()

        binding?.btncalc?.setOnClickListener {
            if(validateMetricUnits()) {
                val heightValue: Float = binding?.etmetricunitheight?.text.toString().toFloat()
                val weightValue: Float = binding?.etmetricunitweight?.text.toString().toFloat()
                val bmi = weightValue/(heightValue*heightValue)
                displayBMIResult(bmi)
            } else {
                Toast.makeText(applicationContext,"Please Enter Valid Values",Toast.LENGTH_SHORT).show()
            }
        }

        binding?.rgunits?.setOnCheckedChangeListener {
            _, checkedId: Int ->
            if(checkedId==R.id.rbmetricunits) {
                makeVisibleMetricUnitView()
            } else {
                makeVisibleUsUnitView()
            }
        }

    }

    private fun validateMetricUnits(): Boolean {
        var isValid: Boolean = true
        if(binding?.etmetricunitweight?.text.toString().isEmpty()) {
            isValid=false
        } else if(binding?.etmetricunitheight?.text.toString().isEmpty()) {
            isValid=false
        }
        return isValid
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f)<=0) {
            bmiLabel = "Very Severely Underweight"
            bmiDescription = "Oops! You really need to take Better Care of Yourself! Eat a Lot"
        } else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0) {
            bmiLabel = "Severely Underweight"
            bmiDescription = "Oops! You Really need to take Better Care of Yourself Eat a Lot"
        } else if(bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You Really Need to take Better Care of Yourself! Eat a Lot"
        } else if(bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a Good Shape!"
        } else if(bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You Really Need to take Better Care of Yourself! Workout a Lot"
        } else if(bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0) {
            bmiLabel = "Obese Class | (Moderatly Obese)"
            bmiDescription = "Oops! You Really Need to take Better Care of Yourself! Workout a Lot"
        } else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0) {
            bmiLabel = "Obese Class || (Severely Obese)"
            bmiDescription = "OMG! You are in a Very Dangerous Condition! Act Now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely Obese)"
            bmiDescription = "OMG! You are in a Very Dangerous Condition! Act Now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.lldisplaybmiresult?.visibility = View.VISIBLE
        binding?.tvbmivalue?.text = bmiValue
        binding?.tvbmitype?.text = bmiLabel
        binding?.tvbmidescription?.text = bmiDescription
    }

    private fun makeVisibleMetricUnitView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilmetricunitweight?.visibility = View.VISIBLE
        binding?.tilmetricunitheight?.visibility = View.VISIBLE
        binding?.tilusmetricunitweight?.visibility = View.GONE
        binding?.tilmetricusunitheightfeet?.visibility = View.GONE
        binding?.tilmetricusunitheightinch?.visibility = View.GONE

        binding?.etmetricunitheight?.text!!.clear()
        binding?.etmetricunitweight?.text!!.clear()
        binding?.lldisplaybmiresult?.visibility = View.INVISIBLE

    }

    private fun makeVisibleUsUnitView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.tilmetricunitweight?.visibility = View.INVISIBLE
        binding?.tilmetricunitheight?.visibility = View.INVISIBLE
        binding?.tilusmetricunitweight?.visibility = View.VISIBLE
        binding?.tilmetricusunitheightfeet?.visibility = View.VISIBLE
        binding?.tilmetricusunitheightinch?.visibility = View.VISIBLE

        binding?.etusmetricunitheightfeet?.text!!.clear()
        binding?.etusmetricunitweight?.text!!.clear()
        binding?.etusmetricunitheightinch?.text!!.clear()
        binding?.lldisplaybmiresult?.visibility = View.INVISIBLE

    }

}