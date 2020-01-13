package com.example.trainingtofindthebeat

import androidx.appcompat.app.AppCompatActivity

object SMaualAPI {

    // In order to keep things DRY, consider what might need to happen with regards to what the
    // search params are to get Playlists and to fetch the right thing from SharedPreferences
    // rather than writing the next methods for all 3 dance genres. Hmm.


    var desiredPlaylist: String = ""
    var desiredTrack: String = ""
    var possibleTracks = ArrayList<String>()

    fun getSalsaSong() {
        // make API call to get all "Salsa Nation" songs
        obtainPlaylistList()
        // parse through until the name of the public owner whatever is Spotify + get those digits
        ownerName()
        // make API call to get list of songs for that playlist
        getTrackList()
        // parse through list to get a list of their IDs (order will be implied).
        trackIDs()
        // run a .Shuffle on the list then get a the list of songs that have already been
        // assessed on for SALSA
        // iterate through it with a ! (not) .Contains to find the first song that isn't in the already seen songs, return that, short-circuit it
        // return the String for the ID of that track
        findNewSong()
    }
    fun obtainPlaylistList() {
//        actually make the API call to get playlists associated with a specific keyword
    }

    fun ownerName() {
//        actually parse through the playlist to find the ID of the first one curated by Spotify
        desiredPlaylist = ""
    }

    fun getTrackList() {
//        actually make the API call to get a list of all the songs associated with
//        "desiredPlaylist value
        }


    fun trackIDs() {
//        parse through Tracklist and pull the IDs for the tracks
//        put in possibleTracks
    }
    fun findNewSong() {
//        shuffle possibleTracks

//        iterate over possibleTracks. When first song that's not already in (Main
//        Activity's) salsaSongs ArrayList happens, return its ID number

    }


}