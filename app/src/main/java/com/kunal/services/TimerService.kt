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
import android.os.Binder


class TimerService : Service() {

    private val TAG = "TimerService"
    private val timer = Timer()

    private val myBinder: IBinder = MyLocalBinder()

    override fun onCreate() {
        super.onCreate()
        callTimer()
        Log.e(TAG, "onCreate: ")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.e(TAG, "onStart: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val serviceType = intent?.getStringExtra("serviceType")
        if (serviceType == "Foreground") {
            createNotification()
        }
        Log.e(TAG, "Service Started.")
        return START_NOT_STICKY
    }

    fun callTimer() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                Log.e(TAG, "Timer called after 5 seconds.")
                callTimer()
            }
        }, 5000)
    }

    private fun createNotification() {
        val id = "TimerService"
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val name: CharSequence = id
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)
            channel.description = id
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, id)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Timer Service")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentText("Timer is running")

        startForeground(1001, builder.build())
    }


    class MyLocalBinder : Binder() {
        fun getService(): TimerService {
            return TimerService()
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return myBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.e(TAG, "onDestroy: ")
    }
}