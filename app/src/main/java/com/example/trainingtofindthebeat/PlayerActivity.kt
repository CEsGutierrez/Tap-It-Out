package com.example.trainingtofindthebeat

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.trainingtofindthebeat.SManualAPI.CURRENT_SALSA_TRACK
import com.example.trainingtofindthebeat.SManualAPI.TEMPO
import kotlinx.android.synthetic.main.activity_player.*
import java.util.*


class PlayerActivity : AppCompatActivity() {


    var TIME_STAMP_LIST= arrayListOf<Int>()
    var START_TIME = 0
    var END_TIME:Long = 0
    var BETTER_START_TIME:Long = 0
    var FUNCTIONAL_AA  = arrayListOf<Int>()
    var FUNCTIONAL_TAP  = arrayListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupListeners()
        CURRENT_SALSA_TRACK = ProgressActivity.pickSalsaSong()
        SManualAPI.getTrackTempo()
    }


    val standInArray = arrayListOf(0, 4000, 8000, 12000)


    fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(animation, scaleX, scaleY)

        animator.repeatCount = 80
        val durationValue = TEMPO / 2 // accounts for expanding and contracting action in the
        // annimation
        animator.setDuration(durationValue)
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()

    }


    fun setupListeners() {
        start_button.setOnClickListener {
            recordSTART_TIME()
            recordBETTER_START_TIME()
//            SServicePlayer.play("spotify:track:3whrwq4DtvucphBPUogRuJ")

            SServicePlayer.play("spotify:track:${CURRENT_SALSA_TRACK}")
            scaler()
        }

        stop_button.setOnClickListener {
            recordEND_TIME()
            val score = calculateScore()
//            Toast.makeText(this, "Hi there, hang on for your score: ", Toast
//                .LENGTH_LONG).show()
            displayStars(score)
            SServicePlayer.disconnect()
        }

        tap.setOnClickListener {
            recordTime()
        }
    }

    fun displayStars(score: Double) {
        if (score < 20) {
            star_1.visibility = View.VISIBLE
            star_2.visibility = View.VISIBLE
            star_3.visibility = View.VISIBLE
        }
        else if (score < 100) {
            star_1.visibility = View.VISIBLE
            star_2.visibility = View.VISIBLE
        }
        else
        {star_1.visibility = View.VISIBLE}
    }

    fun recordTime() {
        var time = System.currentTimeMillis()
        var now = time.toLong().toInt()
        addTimeToArray(now)
        }

    fun addTimeToArray(time: Int ) {
        var length = TIME_STAMP_LIST.size
        if (length == 0)
            TIME_STAMP_LIST.add(0, time)
        else
            TIME_STAMP_LIST.add(length, time)
    }

    fun recordSTART_TIME() {
        val time = System.currentTimeMillis()
        START_TIME = time.toInt()
    }

    fun recordEND_TIME() {
        END_TIME = System.currentTimeMillis()
    }

    fun recordBETTER_START_TIME() {
        BETTER_START_TIME = System.currentTimeMillis()
    }

    fun matchRanges(tapTimes: ArrayList<Int>, AATimes: ArrayList<Int>) {
        var modifiedRange = arrayListOf<Int>()
        var maxTime:Int

        if (tapTimes.size < AATimes.size)
        {maxTime = tapTimes.get(index = tapTimes.size - 1)
            println("Tap times is smaller, maxTime is $maxTime")
            for (time in AATimes) {
                while (time < maxTime) modifiedRange.add(time)
            }
            FUNCTIONAL_AA = modifiedRange

        }
        else {
            maxTime = AATimes.get(index = AATimes.size - 1)
            println("Tap times is smaller, maxTime is $maxTime")
            for (time in tapTimes) {
                while (time < maxTime) modifiedRange.add(time)
            }
            FUNCTIONAL_TAP = modifiedRange
        }
    }


    fun calculateScore():Double {


        // gets the AA for the track, then converts its values to integers in MS
        val AA = SManualAPI.getTrackAA()
        val AATimesMS = QuizActivity.aaTimecCnverter(AA, stopTime = END_TIME,
            startTime = BETTER_START_TIME)

//         calculates the tap times in integers in MS
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


