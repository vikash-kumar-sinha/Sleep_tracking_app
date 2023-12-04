package com.example.sleep_tracking_app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var startSleepTrackingButton: Button
    private lateinit var elapsedTimeTextView: TextView
    private lateinit var trackSleepButton: Button

    private val handler = Handler(Looper.getMainLooper())
    private var startTimeMillis = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContentView(R.layout.activity_main)

        startSleepTrackingButton = findViewById(R.id.startSleepTrackingButton)
        elapsedTimeTextView = findViewById(R.id.elapsedTimeTextView)
        trackSleepButton = findViewById(R.id.trackSleepButton)

        startSleepTrackingButton.setOnClickListener {
            startTimeMillis = System.currentTimeMillis()
            handler.postDelayed(updateElapsedTime, 1000)
        }

        trackSleepButton.setOnClickListener {
            val intent = Intent(this, TrackSleepActivity::class.java)

            // Set start and end times as extras
            val currentTimeMillis = System.currentTimeMillis()
            val endTimeMillis = currentTimeMillis + (8 * 60 * 60 * 1000) // Assuming 8 hours of sleep

            intent.putExtra("startTime", currentTimeMillis)
            intent.putExtra("endTime", endTimeMillis)

            startActivity(intent)
        }
    }

    private val updateElapsedTime = object : Runnable {
        override fun run() {
            val currentTimeMillis = System.currentTimeMillis()
            val elapsedTimeMillis = currentTimeMillis - startTimeMillis

            val hours = (elapsedTimeMillis / (1000 * 60 * 60)) % 24
            val minutes = (elapsedTimeMillis / (1000 * 60)) % 60
            val seconds = (elapsedTimeMillis / 1000) % 60

            val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

            elapsedTimeTextView.text = "Elapsed Time: $formattedTime"

            handler.postDelayed(this, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateElapsedTime)
    }
}
