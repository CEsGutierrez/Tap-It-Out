package com.example.trainingtofindthebeat

import khttp.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

object SManualAPI {

    var BEARER_TOKEN = "" // Used as a variable for all other API calls. Assignment is triggered
    // through getToken function based on Connect Button onClickListener

    // will hold Spotify-owned playlist ID which will be the "approved" playlist
    var SALSA_PLAYLIST_ID = ""
    var BACHATA_PLAYLIST_ID = ""
    var TANGO_MILONGA_PLAYLIST_ID = ""

    // will hold randomized, curated track IDs for tracks from approved playlist. Randomization
    // makes it easier to pick from these track collections later.
    var SALSA_TRACKS = arrayListOf<String>()
    var BACHATA_TRACKS = arrayListOf<String>()
    var TANGO_MILONGA_TRACKS = arrayListOf<String>()

    var CURRENT_SALSA_TRACK = ""


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


    fun getPlaylistId() { // gets the playlist ID for "salsa cubana" related playlist
        // curated by Spotify

        val searchKeyword = "Salsa Cubana"
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
            SALSA_PLAYLIST_ID = playlistID
    }

    fun getTrackList() {

        var playlist_id = SALSA_PLAYLIST_ID

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
            Thread.sleep(500);
        }
        tracks.shuffle()
        SALSA_TRACKS = tracks

    }

    fun getTrackAA(track_id: String): ArrayList<String> {

        var track_id = track_id
        CURRENT_SALSA_TRACK = track_id
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
