package com.example.trainingtofindthebeat

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SManualAPI {

    val url = "https://api.spotify.com/v1/search"
    val key1 = "q"
    val val1 = "Salsa Nation"
    val key2 = "type"
    val val2 = "Playlist"
    val key3 = "Bearer"
    private val val3 =
        "BQBjuWwH9KmK5N7f71xqpBFIqPgaPuF4LiA7CHtFrplmArBmlpqH3p-dAAe68Sk9_fsW9WBSybTJVvVUnIpKS1g7FtHLxILUVkxqNxnJfh7KY1nIaELlVI6dJmWMESHZKei_xpjadvL7q0m3xCUXFd41B3WOi7PzFIO5uQjhog"

    fun getPlaylistId(): String {
        var requestUrl = url;
        requestUrl += "?";
        requestUrl += key1;
        requestUrl += "=";
        requestUrl += val1;
        requestUrl += "&";
        requestUrl += key2;
        requestUrl += "=";
        requestUrl += val2;
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Authorization", val3);
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");

        var finished = false;
        var returnStatement =

        runBlocking {
            GlobalScope.launch {
                val temp = khttp.get(url = requestUrl, headers = headerMap);
                val owner = temp.jsonObject.getJSONArray("items").getJSONObject(0)
                    .getJSONObject("owner").getString("display_name");
                finished = true;

            }

        }
        while (!finished) {
            Thread.sleep(500);
        }
        return owner;
    }
}

//
//
//
//
//import com.google.gson.Gson
//
//class SMaualAPI {
//
//    fun onCreate() {
//
//        obtainPlaylistList()
//
//    }
//
//
//    // In order to keep things DRY, consider what might need to happen with regards to what the
//    // search params are to get Playlists and to fetch the right thing from SharedPreferences
//    // rather than writing the next methods for all 3 dance genres. Hmm.
//
//
//    var desiredPlaylist: String = ""
//    var desiredTrack: String = ""
//    var possibleTracks = ArrayList<String>()
//
//
//
//
//
//
//
//    fun getSalsaTrack() {
//        // make API call to get all "Salsa Nation" songs
//        obtainPlaylistList()
//        // parse through until the name of the public owner whatever is Spotify + get those digits
//        ownerName()
//        // make API call to get list of songs for that playlist
//        getTrackList()
//        // parse through list to get a list of their IDs (order will be implied).
//        trackIDs()
//        // run a .Shuffle on the list then get a the list of songs that have already been
//        // assessed on for SALSA
//        // iterate through it with a ! (not) .Contains to find the first song that isn't in the already seen songs, return that, short-circuit it
//        // return the String for the ID of that track
//        findNewSong()
//    }
//
////    var targetPlaylist: String = "Salsa Nation"
////
////    val url = "https://api.spotify.com/v1/search"
////    val key1 = "q"
////    val val1 = targetPlaylist
////    val key2 = "type"
////    val val2 = "Playlist"
////    val key3 = "Bearer"
////    private val val3 = "BQCtG1wOXSO22zeNdTjFn_tPOZe_KlTtOoS1KMNoTZrvqajX6WNriTvgPY15sQFTJ4VX3QBV-fQyJD3M7XyCnO4C9i3ZW5-ssFvq_DOqdnUNPuNk6j7yzxMGnyUv4jdCwE8-64X9EZQvsbIJSTmK-dacQoPNIko"
////
////    var playlistResponse = ""
//
//    fun obtainPlaylistList():String {
//
//        var targetPlaylist: String = "Salsa Nation"
//
//        val url = "https://api.spotify.com/v1/search"
//        val key1 = "q"
//        val val1 = "Salsa Nation"
//        val key2 = "type"
//        val val2 = "Playlist"
//        val key3 = "Bearer"
//        val val3 = "BQCdzRtK765YUVaMrT-4R68xQM7JUan-1pskZvRoJj3sddwbm172qcYulCZXc_ECwRrrTf74lkWzg6ojfsmH3XBgrJQjQMkcj80LJ2evXEDO9_DUwIO0p-J7slUFBdwvSwEOgOKEA3gWEnrjT-FK7O9_fzmHshY"
//
//        var playlistResponse = ""
//
//
////        actually make the API call to get playlists associated with a specific keyword
//        try{
//        val response : khttp.responses.Response = khttp.get(
//            url = url,
//            params = mapOf(
//                key1 to val1, key2 to val2, key3 to val3
//            )
//        );
//            val returnStatement = Gson().toJson(response).toString()
//            return returnStatement
//        }
//
//        catch(error: Exception){
//            println(error)
//            return error.toString()
//            }
//        }
//
//    fun ownerName() {
//
////        actually parse through the playlist to find the ID of the first one curated by Spotify
//        desiredPlaylist = ""
//    }
//
//    fun getTrackList() {
////        actually make the API call to get a list of all the songs associated with
////        "desiredPlaylist value
//        }
//
//
//    fun trackIDs() {
////        parse through Tracklist and pull the IDs for the tracks
////        put in possibleTracks
//    }
//    fun findNewSong() {
////        shuffle possibleTracks
//
////        iterate over possibleTracks. When first song that's not already in (Main
////        Activity's) salsaSongs ArrayList happens, return its ID number
//
//    }
//
//
//}
//