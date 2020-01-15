package com.example.trainingtofindthebeat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupListeners()
    }

    override fun onStop() {
        super.onStop()
        SServicePlayer.disconnect()
    }


    fun setupListeners() {
        start_button.setOnClickListener {
            recordStartTime()

        }
        stop_button.setOnClickListener() {
            onStop()
        }
        tap.setOnClickListener() {
            recordTime()
        }
    }

    var timeStampList= arrayListOf<String>()

    fun recordTime() {
        var time = System.currentTimeMillis()
        var now = time.toLong().toString()
        addTimeToArray(now)
        }

    fun addTimeToArray(time: String ) {
        var length = timeStampList.size
        if (length == 0)
            timeStampList.add(0, time)
        else
            timeStampList.add(length, time)
    }


    var startTime = ""

    fun recordStartTime() {
        var time = System.currentTimeMillis()
        var now = time.toLong().toString()
        startTime = now
    }

}