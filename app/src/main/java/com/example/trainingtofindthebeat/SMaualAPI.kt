package com.example.trainingtofindthebeat

import khttp.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

object SManualAPI {

    var BEARER_TOKEN = "" // Used as a variable for all other API calls. Assignment is triggered
    // through getToken function based on Connect Button onClickListener

    var GENRE = ""

    // will hold Spotify-owned playlist ID which will be the "approved" playlist
    var SALSA_PLAYLIST_ID = ""
    var BACHATA_PLAYLIST_ID = ""
    var RUMBA_PLAYLIST_ID = ""


    // will hold randomized, curated track IDs for tracks from approved playlist. Randomization
    // makes it easier to pick from these track collections later.
    var SALSA_TRACKS = arrayListOf<String>()
    var BACHATA_TRACKS = arrayListOf<String>()
    var RUMBA_TRACKS = arrayListOf<String>()

    var CURRENT_SALSA_TRACK = ""
    var CURRENT_BACHATA_TRACK = ""
    var CURRENT_RUMBA_TRACK = ""


    var TEMPO:Long = 0


    fun getToken() {

        val id = BuildConfig.TOKEN_IDENTIFICATION // encoded client_id and client_secrets from
        // Spotify

        var response = "" // Used to parse response for just key


        var requestUrl = "https://accounts.spotify.com/api/token?grant_type=client_credentials"
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Authorization", "Basic $id"); // Used to compose call

        var finished = false; // Used for pacing

        runBlocking {
            GlobalScope.launch {
                val temp = khttp.post(url = requestUrl, headers = headerMap)
                response = temp.jsonObject.getString("access_token")
                BEARER_TOKEN = response
                finished = true;
            }
        }
        while (!finished) {
            Thread.sleep(500); // forces pause while waiting for BEARER TOKEN assignment, I think
        }
    }

    fun getPlaylistId(searchKeyword: String): String { // gets the playlist ID for "whatever
        // playlist password was passed into it "

        val searchKeyword = searchKeyword
        val idealOwner = "spotify"

        var requestUrl = "https://api.spotify.com/v1/search?q=$searchKeyword&type=playlist"
        val token = "Bearer $BEARER_TOKEN"
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", token);

        var finished = false;
        var playlistID = ""

        runBlocking {
            GlobalScope.launch {
                val temp = khttp.get(url = requestUrl, headers = headerMap)
                val playlistArray = temp.jsonObject.getJSONObject("playlists").getJSONArray("items")

                // iterates over array to find where a playlist is curated by "spotify" then returns that ID and exist the loop
                for (i in 0 until playlistArray.length()) {
                    val item = playlistArray.getJSONObject(i).getJSONObject("owner").getString("display_name").toLowerCase()
                    if (item == idealOwner)
                        playlistID = playlistArray.getJSONObject(i).getString("id")
                        break
                    }
                finished = true;
                }
            }
            while (!finished) {
                Thread.sleep(500);
            }
        return playlistID
    }

    fun getTrackList(playlist_id: String): ArrayList<String> {

        var playlist_id = playlist_id

        var requestUrl = "https://api.spotify.com/v1/playlists/";
        requestUrl += playlist_id;
        requestUrl += "/tracks";

        val token =
            "Bearer $BEARER_TOKEN"
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", token);

        var finished = false;
        var tracks = arrayListOf<String>()

        runBlocking {
            GlobalScope.launch {

                val temp = khttp.get(url = requestUrl, headers = headerMap)
                val arrayOfObjectfromApi = temp.jsonObject.getJSONArray("items")
                        for (i in 0 until arrayOfObjectfromApi.length()) {
                            val item = arrayOfObjectfromApi.getJSONObject(i).getJSONObject("track").getString("id")
                            tracks.add(item).toString()
                        }

                finished = true;
            }
        }
        while (!finished) {
        }
        tracks.shuffle()
        return tracks

    }

    fun getTrackTempo(track_id: String) {

        var track_id = track_id
        var requestUrl = "https://api.spotify.com/v1/audio-features/"
        requestUrl += track_id;

        val token = "Bearer $BEARER_TOKEN"
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", token);

        var bpm:Long = 0

        var finished = false;

        runBlocking {
            GlobalScope.launch {
                val temp = get(url = requestUrl, headers = headerMap)
                val tempo = temp.jsonObject.getString("tempo")
                finished = true;
                bpm = 60000/(tempo.toFloat()).toInt().toLong()
                val supertemp = bpm::class.java.toString()
                TEMPO = bpm
            }
        }
        while (!finished) {
            Thread.sleep(500);
        }
    }

    fun getTrackAA(track_id: String): ArrayList<String> {

        var track_id = track_id
        var requestUrl = "https://api.spotify.com/v1/audio-analysis/"
        requestUrl += track_id;

        val token = "Bearer $BEARER_TOKEN"
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", token);

        var beatsArray = arrayListOf<String>()
        var finished = false;

        runBlocking {
            GlobalScope.launch {
                val temp = get(url = requestUrl, headers = headerMap)
                val arrayOfObjectsFromApi = temp.jsonObject.getJSONArray("beats")
                for (i in 0 until arrayOfObjectsFromApi.length()) {
                    val item = arrayOfObjectsFromApi.getJSONObject(i).getString("start")
                    beatsArray.add(item.toString())
                }

                finished = true;
            }
        }
        while (!finished) {
            Thread.sleep(500);
        }
        return beatsArray;
    }

}
