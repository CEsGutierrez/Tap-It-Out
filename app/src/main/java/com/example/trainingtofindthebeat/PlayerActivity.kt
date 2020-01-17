package com.example.trainingtofindthebeat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.trainingtofindthebeat.SManualAPI.CURRENT_SALSA_TRACK
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
    }

//    override fun onStop() {
//        super.onStop()
//        SServicePlayer.disconnect()
//    }


    fun setupListeners() {
        start_button.setOnClickListener {
            recordSTART_TIME()
            recordBETTER_START_TIME()
            CURRENT_SALSA_TRACK = ProgressActivity.pickSalsaSong()
            SServicePlayer.play("spotify:track:${SManualAPI.CURRENT_SALSA_TRACK}")

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

        println("I am the tests! * * * * * * * * ")
        println("I am the tests! * * * * * * * * ")
        println("I am the tests! * * * * * * * * ")
        println("I am the tests! * * * * * * * * ")
        println("I am the tests! * * * * * * * * ")
        println("I am the tests! * * * * * * * * ")

        // gets the AA for the track, then converts its values to integers in MS
        val AA = SManualAPI.getTrackAA(CURRENT_SALSA_TRACK)
        println("I am AA: $AA")
        println("I am End Time $END_TIME")
        println("I am Start Time $START_TIME")
        val AATimesMS = QuizActivity.aaTimecCnverter(AA, stopTime = END_TIME,
            startTime = BETTER_START_TIME)
        println("I am AATimesMS: $AATimesMS")

//        println("I am time stamp list $TIME_STAMP_LIST")

//         calculates the tap times in integers in MS
        val TapTimesMS = QuizActivity.tapTimeConverter(TIME_STAMP_LIST, START_TIME)
        println("I am TapTimesMS: $TapTimesMS")

        // removes introductory part of the song for both AA and Tap data(30 seconds)
        var functionalAA = QuizActivity.removeIntro(AATimesMS)
        println("I am functionalAA $functionalAA")
        var functionalTap = QuizActivity.removeIntro(TapTimesMS)
        println("I am functionalTap $functionalTap")

        // Modified the appropriate range to match the size so that the least number of data
        // points are being analyzed. Has to be here to actually touch these variables in a
        // meaningful way.
//        matchRanges(tapTimes = FUNCTIONAL_TAP, AATimes = FUNCTIONAL_AA)
////        println("I am matched ranges $FUNCTIONAL_AA")
////        println("I am matched ranges $FUNCTIONAL_TAP")

        // clusters the times into arrays that hold 10 seconds worth of information
        val clusteredAA = QuizActivity.groupTimes(functionalAA)
        println("I am clustered AA: $clusteredAA")
        val clusteredTap = QuizActivity.groupTimes(functionalTap)
        println("I am clustered Tap: $clusteredTap")

        // calculates the average differences in times of clustered times
        val averagedifferenceAA = QuizActivity.averageDiffInTimes(clusteredAA)
        println("I am averagedifferenceAA: $averagedifferenceAA")
        val averageddifferenceTap = QuizActivity.averageDiffInTimes(clusteredTap)
        println("I am averageddifferenceTap: $averageddifferenceTap")

        // compares the averages in times to compose a final score
        val finalScore = QuizActivity.compareAverages(averagedifferenceAA, averageddifferenceTap)
        println("I am final score: $finalScore")

        return finalScore

    }

}