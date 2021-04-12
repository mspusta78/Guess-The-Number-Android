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

        binding.btnHighScore.setOnClickListener {
            val intent = Intent(this, activity_high_score::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


        if (guessedNumber > 100){
            // your guess is too high
        }

        if (guessedNumber < 100){
            // your guess is too low
        }

        if (guessedNumber == 100){
            //you won
        }

        if (guessedNumber == 100 && numberOfTries > 0){
            // congratulations you guessed the number

        }

        getIncomingIntent()

    }

    private fun getIncomingIntent(){

    }

}