package com.yourwebsite.guessthenumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.yourwebsite.guessthenumber.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.util.concurrent.TimeUnit

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

    private fun getIncomingIntent(){

    }

}