package com.kunal.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnForeground: Button
    private lateinit var btnBackgroundService: Button
    private lateinit var btnStopService: Button
    private lateinit var btnBoundService: Button
    private lateinit var btnOpenIntent: Button
    private lateinit var connection: ServiceConnection

    private val foregroundService by lazy {
        Intent(this, TimerForegroundService::class.java)
    }

    private val backgroundService by lazy {
        Intent(this, TimerBackgroundService::class.java)
    }

    private val boundService by lazy {
        Intent(this, TimerBoundService::class.java)
    }

    lateinit var service: TimerBoundService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnForeground = findViewById(R.id.btnForegroundService)
        btnBackgroundService = findViewById(R.id.btnBackgroundService)
        btnBoundService = findViewById(R.id.btnBoundService)
        btnStopService = findViewById(R.id.btnStopService)
        btnOpenIntent = findViewById(R.id.btnOpenIntent)

        btnForeground.setOnClickListener {
            startForegroundService()
        }
        btnBackgroundService.setOnClickListener {
            startBackgroundService()
        }
        btnBoundService.setOnClickListener {
            startBoundService()
        }
        btnStopService.setOnClickListener {
            stopForegroundService()
        }

        btnOpenIntent.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }

        connection = object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                val binder = p1 as TimerBoundService.MyLocalBinder
                this@MainActivity.service = binder.getService()
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
            }

        }
    }

    private fun startBoundService() {
        bindService(boundService, connection, Context.BIND_AUTO_CREATE)
    }

    private fun startBackgroundService() {
        startService(backgroundService)
    }

    private fun stopForegroundService() {
        stopService(foregroundService)
        stopService(backgroundService)
    }

    private fun startForegroundService() {
        ContextCompat.startForegroundService(this, foregroundService)
    }
}