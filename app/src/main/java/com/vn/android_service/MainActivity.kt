package com.vn.android_service

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vn.android_service.dto.Song
import com.vn.android_service.service.MyService

class MainActivity : AppCompatActivity() {
    private lateinit var edtDataIntent:EditText
    private lateinit var btnStartService: Button
    private lateinit var btnStopService: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtDataIntent = findViewById(R.id.edt_data_intent)
        btnStartService = findViewById(R.id.btn_start_service)
        btnStopService = findViewById(R.id.btn_stop_service)

        btnStartService.setOnClickListener {
            onClickStartService()
        }
        btnStopService.setOnClickListener {
            onClickStopService()
        }
    }

    private fun onClickStopService() {
        Intent(this, MyService::class.java).also {
            stopService(it)
        }
    }

    private fun onClickStartService() {
        val song = Song(
            "Title 1",
            "Single 1",
            R.drawable.download,
            R.raw.file_music
        )

        val intent = Intent(this, MyService::class.java).apply {
            putExtra("object_song", song)
        }

        startService(intent)
    }
}