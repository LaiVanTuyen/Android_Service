package com.vn.android_service.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.vn.android_service.R
import com.vn.android_service.application.MyApplication.Companion.CHANNEL_ID
import com.vn.android_service.dto.Song

class MyService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        Log.e("MyService", "MyService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val song = intent?.getSerializableExtra("object_song") as? Song

        song?.let {
            startMusic(it)
            sendNotification(it)
        }

        return START_NOT_STICKY
    }

    private fun startMusic(song: Song) {
        mediaPlayer = MediaPlayer.create(applicationContext, song.resources).apply {
            start()
        }
    }

    @SuppressLint("ForegroundServiceType")
    private fun sendNotification(song: Song) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, MyService::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )

        val remoteViews = RemoteViews(packageName, R.layout.layout_custom_notification).apply {
            setTextViewText(R.id.tv_title, song.title)
            setTextViewText(R.id.tv_single_song, song.single)
            setImageViewBitmap(R.id.img_song, BitmapFactory.decodeResource(resources, song.image).also {
                if (it == null) {
                    Log.e("MyService", "Failed to decode image resource: ${song.image}")
                }
            })
            setImageViewResource(R.id.img_play, R.drawable.play_circle_24px)
            setImageViewResource(R.id.img_stop, R.drawable.stop_circle_24px)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentIntent(pendingIntent)
            setCustomContentView(remoteViews)
            setSound(null)
        }.build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MyService", "MyService onDestroy")
        mediaPlayer.release()
    }
}