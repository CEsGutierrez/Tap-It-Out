package com.example.trainingtofindthebeat

import khttp.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

object SManualAPI {

//    val token =
//    "Bearer BQB14kLa4n1nzQJglCSDYEQUKjduZOXKkVeDsLrUeQ__HtNKZxZjIWuRYdC2aAoU4W0CrOsT_jVQEDJPopOmwb-erBHXVrFVoyDnWmIH7Ttfs2nPgtjbzi3Jx8gqCkJw4lYGW-mEsgG6O7klDUnfqxE2sqA-lKQxrqIXKj7V-Q"


    fun getPlaylistId(): String { // I ACTUALLY WORK
        var requestUrl = "https://api.spotify.com/v1/search";
            requestUrl += "?";
            requestUrl += "q";
            requestUrl += "=";
            requestUrl += "Salsa Nation";
            requestUrl += "&";
            requestUrl += "type";
            requestUrl += "=";
            requestUrl += "playlist";
            val token = "Bearer BQBykZO6_dFlbvriN8x_YF3OyQCnoRJ-n6NTH02pZC0Tz-skUljAv2f1xBxbpIWpCqa0SvuR7DsfqXFjgoBIVaKRQBx9495-DgzgXK-11gqamXUJIGRqlC6vSFFdJfvTf4a69kSmOHuvaNQPhmAVbqkvhY_vXG_FK8eUAsXNWQ"
            val headerMap = mutableMapOf<String, String>();
            headerMap.put("Accept", "application/json");
            headerMap.put("Content-Type", "application/json");
            headerMap.put("Authorization", token);

            var finished = false;
            var owner:String = "BLARG"
            var playlistID = "Boo"

            runBlocking {
                GlobalScope.launch {
                    val temp = khttp.get(url = requestUrl, headers = headerMap)

//    i = 0
 //   until "owner.downcase == "spotify" i+= 1
 //   owner = temp.jsonObject.getJSONObject("playlists").getJSONArray("items").getJSONObject(i).getJSONObject("owner").getString("display_name");

// SO although I'd love to do this as an "until" loop, I think it's a little tricker with parsing
// since string interpolation is frowned upon in this case and I can't easily turn i into an
// integer. So...that means I'm going to take the entire "items" collection into an actual array
// list, and then iterate through it with a BREAK point should the owner ever match "spotify" at
// which point, I'll let that ID be the last one. Like, I'm thinking through the loop to
// continuously be reassigning a variable ID so that when the loop breaks, I don't have to think
// about grabbing anything, it'll just be the last value that was there.

//                    playlistID = temp.jsonObject.toString()

                    playlistID = temp.jsonObject.getJSONObject("playlists").getJSONArray("items")
                        .getJSONObject(0).getString("id")

//                    owner = temp.jsonObject.getJSONObject("playlists").getJSONArray("items").getJSONObject(0).getJSONObject("owner").getString("display_name");

                    finished = true;

                }

            }
            while (!finished) {
                Thread.sleep(500);
            }
            return playlistID;
    }

    fun getTrackList(): String { // I TOTES WORK!

        var playlist_id = "37i9dQZF1DX7cmFV9rWM0u"

        var requestUrl = "https://api.spotify.com/v1/playlists/";
        requestUrl += playlist_id;
        requestUrl += "/tracks";

        // generate a random number between 1 and 100

        val token =
            "Bearer BQDV9HrTCO3AkiuHQzO7ZHlgdpCNPuiulDQu6N0OwGESdEXpKr5yc-FkvS_6DXNeNKcUkMG_CZ8tX40zpfhC3rdMqOLKKMDVr3AEJw42lTnJ-DBszJzWOAjbG50ZlCCicbNMbvgIQK9NvmYIRg8WKFTaD0QK0ffB6Tfhx7n_SQ"
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", token);

        var finished = false;
        var track: String = "BLARG"

        runBlocking {
            GlobalScope.launch {
                val temp = khttp.get(url = requestUrl, headers = headerMap)
                track =
                    temp.jsonObject.getJSONArray("items").getJSONObject(2).getJSONObject("track")
                        .getString("id");

                finished = true;

            }

        }
        while (!finished) {
            Thread.sleep(500);
        }
        return track;

    }



        fun getTrackbeat(): String { // I WORK, I CAN GET A SPECIFIC BEAT (by index number) for a
            // track

        var track_id = "6iLyEBNStoAemStXqGY7qP"
        var requestUrl = "https://api.spotify.com/v1/audio-analysis/"
        requestUrl += track_id;

        var beats: Array<Objects>

        var firstStart:String = ""

        val token = "Bearer BQBykZO6_dFlbvriN8x_YF3OyQCnoRJ-n6NTH02pZC0Tz-skUljAv2f1xBxbpIWpCqa0SvuR7DsfqXFjgoBIVaKRQBx9495-DgzgXK-11gqamXUJIGRqlC6vSFFdJfvTf4a69kSmOHuvaNQPhmAVbqkvhY_vXG_FK8eUAsXNWQ"
        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", token);

        var finished = false;

        runBlocking {
            GlobalScope.launch {
                val temp = khttp.get(url = requestUrl, headers = headerMap)

//                firstStart = temp.jsonObject.toString()
                firstStart = temp.jsonObject.getJSONArray("beats").getJSONObject(10).getDouble("start").toString()



//                track = temp.jsonObject.getJSONArray("items").getJSONObject(2).getJSONObject("track").getString("id");

                finished = true;

            }

        }
        while (!finished) {
            Thread.sleep(500);
        }
        return firstStart;
    }

    fun getTrackAA(): ArrayList<String> { // I WORK, I RETURN AN ARRAY OF BEAT STRINGS, YAAAAAAAS


        var track_id = "6iLyEBNStoAemStXqGY7qP"
        var requestUrl = "https://api.spotify.com/v1/audio-analysis/"
        requestUrl += track_id;

        val token = "Bearer BQBykZO6_dFlbvriN8x_YF3OyQCnoRJ-n6NTH02pZC0Tz-skUljAv2f1xBxbpIWpCqa0SvuR7DsfqXFjgoBIVaKRQBx9495-DgzgXK-11gqamXUJIGRqlC6vSFFdJfvTf4a69kSmOHuvaNQPhmAVbqkvhY_vXG_FK8eUAsXNWQ"
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
