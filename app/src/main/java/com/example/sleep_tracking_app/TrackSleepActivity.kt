package com.example.sleep_tracking_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrackSleepActivity : AppCompatActivity() {

    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_sleep)

        startTimeTextView = findViewById(R.id.startTimeTextView)
        endTimeTextView = findViewById(R.id.endTimeTextView)

        val startTimeMillis = intent.getLongExtra("startTime", 0)
        val endTimeMillis = intent.getLongExtra("endTime", 0)

        val startTime = formatTime(startTimeMillis)
        val endTime = formatTime(endTimeMillis)

        startTimeTextView.text = "Start Time: $startTime"
        endTimeTextView.text = "End Time: $endTime"
    }

    private fun formatTime(timeMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val date = Date(timeMillis)
        val formattedDate = dateFormat.format(date)
        val formattedTime = timeFormat.format(date)

        return "$formattedDate $formattedTime"
    }
}
