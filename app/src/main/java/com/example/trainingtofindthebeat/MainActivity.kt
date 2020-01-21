package com.example.trainingtofindthebeat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.trainingtofindthebeat.SManualAPI.BACHATA_TRACKS
import com.example.trainingtofindthebeat.SManualAPI.SALSA_TRACKS
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
        salsa_train.setEnabled(true)
        bachata_train.visibility = View.VISIBLE
        bachata_train.setEnabled(true)

    }

    fun hideConnectButton() {
        connect_button.visibility = View.INVISIBLE
        connect_button.setEnabled(false)
    }

    private fun setupListeners() {
        salsa_train.setOnClickListener {
            SManualAPI.GENRE = "SALSA"
            if (SALSA_TRACKS.size < 1) {
                SManualAPI.SALSA_PLAYLIST_ID = SManualAPI.getPlaylistId("Salsa Cubana")
                SALSA_TRACKS = SManualAPI.getTrackList(SManualAPI.SALSA_PLAYLIST_ID)
            }
            else {SALSA_TRACKS.shuffle()}
            launchPlayer()
        }

        bachata_train.setOnClickListener {
            SManualAPI.GENRE = "BACHATA"
            if (BACHATA_TRACKS.size < 1) {
                SManualAPI.BACHATA_PLAYLIST_ID = SManualAPI.getPlaylistId("Bachateame")
                BACHATA_TRACKS = SManualAPI.getTrackList(SManualAPI.BACHATA_PLAYLIST_ID)
            }
            else {
                BACHATA_TRACKS.shuffle()
            }
            launchPlayer()
        }
    }
}
