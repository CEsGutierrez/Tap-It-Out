package com.example.trainingtofindthebeat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sets up connect_button listener which will effectively launch the entire app
        connect_button.setOnClickListener {
            showMainButtons()
            hideConnectButton()
            SManualAPI.getToken()
        }
        //sets up all the other buttons' listeners which will determine the functional parts of the app
        setupListeners()
    }

    // function that actually launches the player activity
    fun launchPlayer () {
        SServicePlayer.connect(this) {
            val intent = Intent(this, PlayerActivity::class.java)
            startActivity(intent)
        }
    }

    fun showMainButtons() {
        training_selection.visibility = View.VISIBLE
        salsa_train.visibility = View.VISIBLE
        bachata_train.visibility = View.VISIBLE
        rumba_train.visibility = View.VISIBLE

    }

    fun hideConnectButton() {
        connect_button.visibility = View.INVISIBLE
    }

    private fun setupListeners() {

        salsa_train.setOnClickListener {
            SManualAPI.GENRE = "SALSA"
            SManualAPI.SALSA_PLAYLIST_ID = SManualAPI.getPlaylistId("Salsa Cubana")
            SManualAPI.SALSA_TRACKS = SManualAPI.getTrackList(SManualAPI.SALSA_PLAYLIST_ID)
            launchPlayer()
        }

        bachata_train.setOnClickListener {
            SManualAPI.GENRE = "BACHATA"
            SManualAPI.BACHATA_PLAYLIST_ID = SManualAPI.getPlaylistId("Bachateame")
            SManualAPI.BACHATA_TRACKS = SManualAPI.getTrackList(SManualAPI.BACHATA_PLAYLIST_ID)
            launchPlayer()
        }
        rumba_train.setOnClickListener {
            SManualAPI.GENRE = "RUMBA"
            SManualAPI.RUMBA_PLAYLIST_ID = SManualAPI.getPlaylistId("Rumba Colombiana")
            SManualAPI.RUMBA_TRACKS = SManualAPI.getTrackList(SManualAPI.RUMBA_PLAYLIST_ID)
            launchPlayer()
        }

    }

}
