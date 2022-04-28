package com.example.a10minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a10minworkoutapp.databinding.ActivityExcerciseBinding

class ExcerciseActivity : AppCompatActivity() {

    private var bindings: ActivityExcerciseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        bindings = ActivityExcerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindings?.root)

        setSupportActionBar(bindings?.toolbarExcercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        bindings?.toolbarExcercise?.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}