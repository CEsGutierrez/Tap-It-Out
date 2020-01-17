package com.example.trainingtofindthebeat

object ProgressActivity {


    fun getSalsaScores() {
        //gts the salsa scores from sharedpreferences
        //assigns them to salsaScores
    }
    fun getBachataScores() {
        //gts the bachata scores from sharedpreferences
        //assigns them to bachataScores
    }
    fun getTangoMilScores() {
        //gts the tango and milonga scores from sharedpreferences
        //assigns them to tangoMilongaScores
    }

    var salsaScores = ArrayList<Any>()
    var bachataScores = ArrayList<Any>()
    var tangoMilongaScores = ArrayList<Any>()

    fun getSalsaSongs() {
        getSalsaScores()
        if (salsaScores.size == 0)
            salsaSongs.add("")
        else if (salsaScores.size == 1) {
            var placeholder = ArrayList<Any>(salsaScores)
            val onlySong = placeholder.get(0).toString()
            salsaSongs.add(onlySong)
        }
        else {
            var limit :Int = 3
            salsaScores.forEachIndexed { index, element ->
                if(index % limit == 0)
                    salsaSongs.add(element.toString())
            }
        }
    }

    fun pickSalsaSong():String {
        if (salsaScores.size == 0) {
            return SManualAPI.SALSA_TRACKS[0]
        } else {
            for (track in SManualAPI.SALSA_TRACKS) {
                if (salsaSongs.contains(track)) {
                    return track
                    break
                }
            }
        }
        return "5ubqSAc9LxUS2CsMHcA4kF" // Will return "Como la Flor" by Selena if the user has
        // trained to every song in the playlist
    }


    fun getBachataSongs() {}
    fun getTangoMilongaSongs() {}


    var salsaSongs = ArrayList<String>()
    val bachataSongs = ArrayList<Any>()
    val tangoMilongaSongs = ArrayList<Any>()

}