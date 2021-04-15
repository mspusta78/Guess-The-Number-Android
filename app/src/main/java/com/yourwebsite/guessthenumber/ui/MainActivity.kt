package com.yourwebsite.guessthenumber.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.yourwebsite.guessthenumber.R
import com.yourwebsite.guessthenumber.classes.CurrentAppInfo
import com.yourwebsite.guessthenumber.databinding.ActivityMainBinding
import okhttp3.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var guessedNumber = 0
    var numberOfTries = 3

    var mOkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.loading.visibility = View.VISIBLE

        binding.level1.setOnClickListener {
            val intent = Intent(this, activity_game_view::class.java)
            intent.putExtra("level", 1)
            startActivity(intent)
        }

        binding.level2.setOnClickListener {
            val intent = Intent(this, activity_game_view::class.java)
            intent.putExtra("level", 2)
            startActivity(intent)
        }

        binding.level3.setOnClickListener {
            val intent = Intent(this, activity_game_view::class.java)
            intent.putExtra("level", 3)
            startActivity(intent)
        }

        binding.btnHighScore.setOnClickListener {
            val intent = Intent(this, activity_high_score::class.java)
            startActivity(intent)
        }

        getIncomingIntent()
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra("playerName")) {
            val playerName = intent.getStringExtra("playerName")
            binding.layoutPlayerInfo.visibility = View.VISIBLE
            binding.playerName.text = playerName

            CurrentAppInfo.playerName = playerName.toString()
        }
    }



}