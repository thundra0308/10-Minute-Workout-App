package com.example.a10minworkoutapp

import android.app.Application

class WorkoutApp:Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}