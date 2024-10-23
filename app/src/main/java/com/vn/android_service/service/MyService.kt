package com.vn.android_service.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.vn.android_service.R
import com.vn.android_service.application.MyApplication.Companion.CHANNEL_ID

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("MyService", "MyService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dataIntent = intent?.getStringExtra("key_data_intent")
        sendNotification(dataIntent)

        return START_NOT_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    private fun sendNotification(dataIntent: String?) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, MyService::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title Notification MyService")
            .setContentText(dataIntent)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MyService", "MyService onDestroy")
    }


}