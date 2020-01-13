package com.example.trainingtofindthebeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.trainingtofindthebeat.SMaualAPI.getSalsaPlaylist
import com.example.trainingtofindthebeat.SMaualAPI.getSalsaSong
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sets up connect_button listener which will effectively launch the entire app
        connect_button.setOnClickListener {
            showMainButtons()
            hideConnectButton()
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

    val salsaScores = ArrayList<Any>()
    val bachataScores = ArrayList<Any>()
    val tangoMilongaScores = ArrayList<Any>()

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

    fun getBachataSongs() {}
    fun getTangoMilongaSongs() {}


    var salsaSongs = ArrayList<String>()
    val bachataSongs = ArrayList<Any>()
    val tangoMilongaSongs = ArrayList<Any>()


    private fun setupListeners() {

        salsa_train.setOnClickListener {
            getSalsaScores()
            // salsaScores is now populated
            getSalsaSongs()
            //salsaSongs is now populated
            getSalsaSong()

        }



        bachata_train.setOnClickListener {}
        tango_train.setOnClickListener {}

        salsa_quiz.setOnClickListener {}
        bachata_quiz.setOnClickListener {}
        tango_quiz.setOnClickListener {}

        progress_button.setOnClickListener {}
    }

}
