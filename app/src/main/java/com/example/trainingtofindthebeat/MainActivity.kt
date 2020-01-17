package com.example.trainingtofindthebeat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

//    var THIS_IS_A_TEST = 1

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
        tango_train.visibility = View.VISIBLE

        quiz_selection.visibility = View.VISIBLE
        salsa_quiz.visibility = View.VISIBLE
        bachata_quiz.visibility = View.VISIBLE
        tango_quiz.visibility = View.VISIBLE

        progress_button.visibility = View.VISIBLE
    }

    fun hideConnectButton() {
        connect_button.visibility = View.INVISIBLE
    }







    private fun setupListeners() {

        salsa_train.setOnClickListener {
            SManualAPI.getPlaylistId()
            SManualAPI.getTrackList()
//            progress_button.setText(SManualAPI.getTrackAA(pickSalsaSong())
//                .toString())
            launchPlayer()

        }



        bachata_train.setOnClickListener {}
        tango_train.setOnClickListener {}

        salsa_quiz.setOnClickListener {}
        bachata_quiz.setOnClickListener {}
        tango_quiz.setOnClickListener {}

        progress_button.setOnClickListener {}
    }

}
