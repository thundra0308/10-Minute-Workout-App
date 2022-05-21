package com.example.a10minworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a10minworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var bindings: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings?.root)

        bindings?.flstart?.setOnClickListener{
            Toast.makeText(applicationContext, "Here We Go Again", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ExcerciseActivity::class.java)
            startActivity(intent)
        }

        bindings?.flbmi?.setOnClickListener{
            val intent = Intent(this, ActivityBMI::class.java)
            startActivity(intent)
        }

        bindings?.flhistory?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        fun onDestroy() {
            super.onDestroy()
            bindings = null
        }

    }
}