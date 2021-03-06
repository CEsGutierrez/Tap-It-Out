package com.example.trainingtofindthebeat

import android.content.Context
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.SpotifyAppRemote.connect

// This object runs the Spotify SDK, which runs the playback functions

object SServicePlayer {

    private const val CLIENT_ID = BuildConfig.CLIENT_ID
    private const val REDIRECT_URI = "com.example.trainingtofindthebeat://"

    private var spotifyAppRemote: SpotifyAppRemote? = null

    private var connectionParams: ConnectionParams = ConnectionParams.Builder(CLIENT_ID)
        .setRedirectUri(REDIRECT_URI)
        .showAuthView(true)
        .build()

    fun connect(context: Context, handler: (connected: Boolean) -> Unit) {
        if (spotifyAppRemote?.isConnected == true) {
            handler(true)
            return
        }
        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                this@SServicePlayer.spotifyAppRemote = spotifyAppRemote
                handler(true)
            }
            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyService", throwable.message, throwable)
                handler(false)
            }
        }
        connect(context, connectionParams, connectionListener)
    }

     fun disconnect() {
//         SpotifyAppRemote.disconnect(spotifyAppRemote)

//        .disconnect function is not supported by spotifyAppRemote. Possibly due to being in
//        "beta" thus .pause call is used instead

        spotifyAppRemote?.playerApi?.pause()
     }

    fun play(uri: String) {
        spotifyAppRemote?.playerApi?.play(uri)
    }
}
