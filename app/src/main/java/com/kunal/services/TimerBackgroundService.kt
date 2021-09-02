package com.kunal.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

class TimerBackgroundService : Service() {

    private val TAG = "TimeForegroundService"
    private val timer = Timer()

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ", )
        startTimer()
    }

    private fun startTimer() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                startTimer()
                Log.e(TAG, "Timer is called after 5 seconds")
            }
        }, 5000)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand: ")
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.e(TAG, "onDestroy: ")
    }
}