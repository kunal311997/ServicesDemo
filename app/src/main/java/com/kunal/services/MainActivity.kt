package com.kunal.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kunal.services.TimerService.MyLocalBinder


class MainActivity : AppCompatActivity() {

    private lateinit var btnForeground: Button
    private lateinit var btnBackground: Button
    private lateinit var btnBound: Button
    private lateinit var btnStopService: Button
    private lateinit var btnOpenIntent: Button
    private lateinit var txtServiceType: TextView
    private lateinit var connection: ServiceConnection
    private lateinit var service: TimerService
    private val TAG = "MainActivity"

    private val serviceIntent by lazy {
        Intent(this, TimerService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnForeground = findViewById(R.id.btnForeground)
        btnBackground = findViewById(R.id.btnBackground)
        btnBound = findViewById(R.id.btnBound)
        btnBound = findViewById(R.id.btnBound)
        btnStopService = findViewById(R.id.btnStopService)
        btnOpenIntent = findViewById(R.id.btnOpenIntent)
        txtServiceType = findViewById(R.id.txtServiceType)
        connection = object : ServiceConnection {
            override fun onServiceDisconnected(componentName: ComponentName) {
            }

            override fun onServiceConnected(
                componentName: ComponentName, service: IBinder
            ) {
                val binder = service as MyLocalBinder
                this@MainActivity.service = binder.getService()
            }
        }
        addOnClickListeners()
    }

    private fun addOnClickListeners() {
        btnForeground.setOnClickListener {
            Log.e(TAG, "addOnClickListeners: btnForeground")
            serviceIntent.putExtra("serviceType", "Foreground")
            ContextCompat.startForegroundService(this, serviceIntent)
            txtServiceType.text = "Foreground service started"
        }

        btnBackground.setOnClickListener {
            Log.e(TAG, "addOnClickListeners: btnBackground")
            serviceIntent.putExtra("serviceType", "Background")
            startService(serviceIntent)
            txtServiceType.text = "Background service started"
        }

        btnBound.setOnClickListener {
            Log.e(TAG, "addOnClickListeners: btnBound")
            bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
            txtServiceType.text = "Bounded service started"
        }

        btnStopService.setOnClickListener {
            Log.e(TAG, "addOnClickListeners: btnStopService")
            stopService(serviceIntent)
            txtServiceType.text = "Service stopped"
        }

        btnOpenIntent.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}