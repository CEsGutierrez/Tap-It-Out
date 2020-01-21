package com.example.trainingtofindthebeat

import java.lang.Math.sqrt
import java.util.*

object QuizActivity {

    // calculates the tap times in integers in MS
    fun tapTimeConverter (timeStampList: ArrayList<Int>, startTime: Int) : ArrayList<Int> {
        val timeStampList = timeStampList
        val startTime = startTime

        var tapTimesMS = arrayListOf<Int>()
        for (time in timeStampList) {
            var temp = time.toInt()
            temp = temp - startTime.toInt()
            tapTimesMS.add(temp)
        }
        return tapTimesMS
    }

    // calculates the MS of the beats in the AA, in addition, it cuts the range of time analyzed to the time before the user hit "Stop"
    fun aaTimecCnverter(AABeats:ArrayList<String>, stopTime: Long, startTime: Long):ArrayList<Int> {
    // converts
        // stringy
        // AA times in seconds in Strings and converts it to AA times in miliseconds in Integers
        val stopTime = stopTime
        val startTime = startTime

        val lengthOfSong = stopTime - startTime

        val aaTimesStrings = AABeats

        val aaTimesMS = arrayListOf<Int>()

        for (time in aaTimesStrings) {
            var temp = (time.toFloat())
            temp *= 1000
            val supertemp = temp.toLong()
            if (supertemp < lengthOfSong) { aaTimesMS.add(temp.toInt())}
        }
        return aaTimesMS
    }

    // removes introductory part of the song for both AA and Tap data(30 seconds)
    fun removeIntro(timeList: ArrayList<Int>) : ArrayList<Int> { // non-destructively eliminates
        // the first 30 seconds of the song's worth of taps
        val i = 30000
        var functionalTimes = arrayListOf<Int>()
        for (time in timeList) {
            if (time > i ) {functionalTimes.add(time)}
        }
        return functionalTimes
    }

    // clusters the times into arrays that hold 10 seconds worth of information
    fun groupTimes (functionalTimes: ArrayList<Int>):ArrayList<List<Int>> {
        var temp = mutableListOf<Int>()
        var groups = arrayListOf<List<Int>>()
        var i = 30000
        var j = 40000
        for (time in functionalTimes) {

            if (time > i && time < j ) {
                temp.add(time)
            }
            else if (time > j){

                val superTemp = arrayListOf(temp).flatten()

                groups.add(superTemp)
                temp.clear()
                i += 10000
                j += 10000
                temp.add(time)
            }
        }
        return groups
    }

    // calculates the average differences in times of clustered times
    fun averageDiffInTimes (groupedTimes: ArrayList<List<Int>>) : ArrayList<Float> {
        var averages = ArrayList<Float>()
        var average = 1.toFloat()
        var sum = 0
        var count = 0

        for (timeGroups in groupedTimes){
            var i = 0
            val limit = timeGroups.size - 1
            while (i < limit) {
                val dif = timeGroups.get(index = (i + 1)) - timeGroups.get(index = i)
                sum += dif
                count += 1
                i += 1
            }

            average = sum.toFloat()/count
            averages.add(average)
            sum = 0
            count = 0
            i = 0
        }
        return averages
    }

    // compares the averages in times to compose a final score
    fun compareAverages(AAAverageDifference : ArrayList<Float>, TapAverageDifference:
    ArrayList<Float>): Double {

        var sumOfDifferences:Double = 1.0
        var count = 0
        var i = 0

        while (i < TapAverageDifference.size) {
            val difference = AAAverageDifference[i] - TapAverageDifference[i]
            var squareValue = (difference * difference).toDouble()
            var absoluteValue = sqrt(squareValue)
            sumOfDifferences += absoluteValue
            count += 1
            i += 1
        }

        var finalScore = sumOfDifferences / count

        return finalScore
    }
}