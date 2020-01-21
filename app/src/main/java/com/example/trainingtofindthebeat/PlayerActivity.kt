package com.example.trainingtofindthebeat

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.trainingtofindthebeat.SManualAPI.CURRENT_BACHATA_TRACK
import com.example.trainingtofindthebeat.SManualAPI.CURRENT_SALSA_TRACK
import com.example.trainingtofindthebeat.SManualAPI.TEMPO
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    // slew of variables needed across the program
    var TIME_STAMP_LIST= arrayListOf<Int>()
    var START_TIME = 0
    var END_TIME:Long = 0
    var BETTER_START_TIME:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        Toast.makeText(this, "The blue note will help you and the song get settled.", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Tap the grey button to the beat.", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "When you stop, we'll tell you how you did!", Toast.LENGTH_LONG).show()
        setupListeners()
        trackAssigner()
    }


    fun trackAssigner() { // depending on the genre that has been chosen, this ensures that the
        // right track is assigned, additionally that it's audio features are gotten and the beat
        // for the song assigned
        if (SManualAPI.GENRE == "SALSA") {
            // Changes the view appearance to red background, trombone, and text "Salsa"
            instrument_1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.trombone))
            activity_announcer.setBackgroundColor(Color.parseColor("#CB0D0F"))
            activity_ann.setText("SALSA")

            CURRENT_SALSA_TRACK = SManualAPI.SALSA_TRACKS[0]
            SManualAPI.getTrackTempo(CURRENT_SALSA_TRACK)
        }
        else if (SManualAPI.GENRE == "BACHATA") {
            // Changes the view appearance to yellow background, congas, and text "Bachata"
            instrument_1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.congas))
            activity_announcer.setBackgroundColor(Color.parseColor("#F8D541"))
            activity_ann.setText("BACHATA")

            CURRENT_BACHATA_TRACK = SManualAPI.BACHATA_TRACKS[0]
            SManualAPI.getTrackTempo(CURRENT_BACHATA_TRACK)
        }
    }


    fun animationScaler() { // controlls the music note there to help the user get started at the
        // start of the song
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(animation, scaleX, scaleY)

        // plays for about 25 "pulses" hopefully long enough for the intro to be over and the user to get settled
        animator.repeatCount = 50

        // accounts for expanding and contracting action in the annimation
        val durationValue = TEMPO / 2
        animator.setDuration(durationValue)
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    // helps player remain modular by passing into it the current working track
    fun genreExtender(): String {
        if(SManualAPI.GENRE == "SALSA") {
            return "spotify:track:${CURRENT_SALSA_TRACK}"
        }
        else {
            return "spotify:track:${CURRENT_BACHATA_TRACK}"
        }
    }

    // triggers start and stop functions as well as their companions
    fun setupListeners() {
        start_button.setOnClickListener {
            recordSTART_TIME()
            SServicePlayer.play(genreExtender())
            animationScaler()
        }

        stop_button.setOnClickListener {
            recordEND_TIME()
            val score = calculateScore()
            displayStars(score)
            SServicePlayer.disconnect()
        }

        tap.setOnClickListener {
            recordTime()
        }
    }

    // displays a different number of stars based score calculations
    fun displayStars(score: Double) {
        score_area.visibility = View.VISIBLE
        if (score < 20) {
            star_1.visibility = View.VISIBLE
            star_2.visibility = View.VISIBLE
            star_3.visibility = View.VISIBLE
            star_4.visibility = View.VISIBLE
            star_5.visibility = View.VISIBLE
        }
        else if (score < 50) {
            star_1.visibility = View.VISIBLE
            star_2.visibility = View.VISIBLE
            star_3.visibility = View.VISIBLE
            star_4.visibility = View.VISIBLE
        }
        else if (score < 100) {
            star_1.visibility = View.VISIBLE
            star_2.visibility = View.VISIBLE
            star_3.visibility = View.VISIBLE
        }
        else if (score < 200) {
            star_1.visibility = View.VISIBLE
            star_2.visibility = View.VISIBLE
        }
        else {
            star_1.visibility = View.VISIBLE
        }
    }

    // records the time of each button press to the tap button and adds it to the collection of taps
    fun recordTime() {
        var time = System.currentTimeMillis()
        var now = time.toLong().toInt()
        var length = TIME_STAMP_LIST.size
        if (length == 0)
            TIME_STAMP_LIST.add(0, now)
        else
            TIME_STAMP_LIST.add(length, now)
        }

    // records the theoretical start of the song playing
    fun recordSTART_TIME() {
        val time = System.currentTimeMillis()
        BETTER_START_TIME = time // accurate to Epoch
        START_TIME = time.toInt() // suitable for Tap time calculations
    }

    // records when the player presses "Stop" button
    fun recordEND_TIME() {
        END_TIME = System.currentTimeMillis()
    }

    fun calculateScore():Double {
        // this section gets the Audio Analysis (AA) for the working track of the genre being
        // currently used.
        var AA:ArrayList<String>
        if (SManualAPI.GENRE == "SALSA") {
            AA = SManualAPI.getTrackAA(CURRENT_SALSA_TRACK)
        }
        else {
            AA = SManualAPI.getTrackAA(CURRENT_BACHATA_TRACK)
        }

        // calculates the MS of the beats in the AA, in addition, it cuts the range of time
        // analyzed to the time before the user hit "Stop"
        val AATimesMS = QuizActivity.aaTimecCnverter(AA, stopTime = END_TIME,
            startTime = BETTER_START_TIME)

        // calculates the tap times in integers in MS
        val TapTimesMS = QuizActivity.tapTimeConverter(TIME_STAMP_LIST, START_TIME)

        // removes introductory part of the song for both AA and Tap data(30 seconds)
        var functionalAA = QuizActivity.removeIntro(AATimesMS)
        var functionalTap = QuizActivity.removeIntro(TapTimesMS)

        // clusters the times into arrays that hold 10 seconds worth of information
        val clusteredAA = QuizActivity.groupTimes(functionalAA)
        val clusteredTap = QuizActivity.groupTimes(functionalTap)

        // calculates the average differences in times of clustered times
        val averagedifferenceAA = QuizActivity.averageDiffInTimes(clusteredAA)
        val averageddifferenceTap = QuizActivity.averageDiffInTimes(clusteredTap)

        // compares the averages in times to compose a final score
        val finalScore = QuizActivity.compareAverages(averagedifferenceAA, averageddifferenceTap)

        return finalScore
    }
}