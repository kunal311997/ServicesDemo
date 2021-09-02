package com.kunal.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*

class TimerForegroundService : Service() {

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
        showNotification()
        return START_NOT_STICKY
    }

    private fun showNotification() {
        val id = TAG
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val name: CharSequence = id
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)
            channel.description = id
            val notificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(applicationContext, id)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Foreground Service")
            .setContentText("Timer is running")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        startForeground(1001, builder.build())
        Log.e(TAG, "showNotification: ")
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