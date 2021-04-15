package com.yourwebsite.guessthenumber.ui

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import com.yourwebsite.guessthenumber.R
import com.yourwebsite.guessthenumber.classes.CurrentAppInfo
import com.yourwebsite.guessthenumber.databinding.ActivityGameViewBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class activity_game_view : AppCompatActivity() {

    private lateinit var binding: ActivityGameViewBinding
    var mOkHttpClient = OkHttpClient()

    var correctNumber = 0
    var maxRandomNumber = 0
    var numberOfTries = 3
    var guessedNumber = ""
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_view)

        binding.endGame.setOnClickListener {
            finish()
        }

        binding.btnGuess.setOnClickListener {
            guessNumber()
        }

        getIncomingIntent()

        setUpNumberPad()

    }

    private fun setUpNumberPad(){
        binding.numberPad.number1.setOnClickListener {
            setGuessNumber("1")
        }

        binding.numberPad.number2.setOnClickListener {
            setGuessNumber("2")
        }

        binding.numberPad.number3.setOnClickListener {
            setGuessNumber("3")
        }

        binding.numberPad.number4.setOnClickListener {
            setGuessNumber("4")
        }

        binding.numberPad.number5.setOnClickListener {
            setGuessNumber("5")
        }

        binding.numberPad.number6.setOnClickListener {
            setGuessNumber("6")
        }

        binding.numberPad.number7.setOnClickListener {
            setGuessNumber("7")
        }

        binding.numberPad.number8.setOnClickListener {
            setGuessNumber("8")
        }

        binding.numberPad.number9.setOnClickListener {
            setGuessNumber("9")
        }

        binding.numberPad.number0.setOnClickListener {
            setGuessNumber("0")
        }

        binding.numberPad.cardViewClear.setOnClickListener {
            guessedNumber = ""
            binding.guessedNumberEditText.setText(guessedNumber)
        }

        binding.numberPad.cardViewDelete.setOnClickListener {
            if (guessedNumber.length != 0){
                guessedNumber = guessedNumber.substring(0, guessedNumber.length - 1)
                binding.guessedNumberEditText.setText(guessedNumber)
            }
        }
    }

    private fun getIncomingIntent(){
        if(intent.hasExtra("level")){
            val level = intent.getIntExtra("level", 1)

            if (level == 1){
                maxRandomNumber = 20
            }else if (level == 2){
                maxRandomNumber = 50
            }else if (level == 3){
                maxRandomNumber = 99
            }

            binding.txtChooseNumber.text = "Choose number between 1 and $maxRandomNumber"

            generateRandomNumber()
        }else{
            maxRandomNumber == 20
        }
    }

    private fun generateRandomNumber(){
        correctNumber = (1..maxRandomNumber).random()
        Log.d("GameView", "generateRandomNumber: correctNumber = $correctNumber")
    }

    private fun setGuessNumber(number: String){
        guessedNumber += number
        binding.guessedNumberEditText.setText(guessedNumber)
    }

    private fun guessNumber(){
        if (binding.guessedNumberEditText.text!!.isBlank()){
            binding.guessedNumberEditText.error = "Enter Number"
        }else{
            if (numberOfTries != 0){
                var intGuessedNumber = guessedNumber.toInt()
                if (intGuessedNumber > correctNumber){
                    // your guess is too high
                    binding.txtNumberChosen.visibility = View.VISIBLE
                    binding.txtNumberChosen.text = "You entered #$intGuessedNumber but it's to high"
                    wrong()
                }else if (intGuessedNumber < correctNumber){
                    // your guess is too low
                    binding.txtNumberChosen.visibility = View.VISIBLE
                    binding.txtNumberChosen.text = "You entered #$intGuessedNumber but it's to low"
                    wrong()
                }else  if (intGuessedNumber == correctNumber && numberOfTries > 0){
                    // congratulations you guessed the number
                    Log.d("GameView", "guessNumber: you won")
                    binding.txtNumberChosen.visibility = View.VISIBLE
                    binding.txtNumberChosen.setTextColor(getColor(R.color.green))
                    binding.txtNumberChosen.text = "You Won #$intGuessedNumber but it's to low"
                    score += 1
                    binding.txtScore.text = "Score: $score"
                    resetGame()
                    showWinDialog()
                }
            }

        }
    }

    private fun showWinDialog(){
        binding.cardViewWin.visibility = View.VISIBLE

        binding.btnPlayAgain.setOnClickListener {
            binding.cardViewWin.visibility = View.GONE
            resetGame()
        }

        binding.btnEndGame.setOnClickListener {
            finish()
        }
    }

    private fun wrong(){
        numberOfTries -= 1
        binding.txtNumberOfTries.text = "Number of tries left: ${numberOfTries.toString()}"

        guessedNumber = ""
        binding.guessedNumberEditText.setText(guessedNumber)

        if (numberOfTries == 0){
            showGameOverDialog()
        }
    }

    private fun showGameOverDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle("Game Over")
            .setMessage("Would you like to play again?")
            .setNegativeButton("Quit") { dialog, which ->
                // End Game and return to main menu
                finish()
            }
            .setPositiveButton("Play Again") { dialog, which ->
                // Respond to positive button press
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetGame(){
        //Call Post API to update high score
        if (checkInternetStatus()){
            updateHighScore()
        }

        generateRandomNumber()
        numberOfTries = 3
        binding.txtNumberOfTries.visibility = View.GONE
        binding.txtNumberChosen.visibility = View.GONE
        guessedNumber = ""
        binding.guessedNumberEditText.setText(guessedNumber)
        binding.txtNumberChosen.setTextColor(Color.RED)
    }

    private fun updateHighScore(){
        val postData = JsonObject()
        postData.addProperty("displayName", CurrentAppInfo.playerName)
        postData.addProperty("score", score)
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val postBody = RequestBody.create(JSON, postData.toString())

        val get = Request.Builder()
            .url("https://guessthenumber123.web.app/api/highscore")
            .post(postBody)
            .build()

        mOkHttpClient.newCall(get).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("error")
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string();
                Log.d("GameView", "onResponse: response: $responseBody")
            }
        })
    }

    private fun checkInternetStatus():Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected) {
            Log.d(
                "GameView",
                "checkInternetStatus: Connected to the internet. Cellular Data: ${activeNetwork?.isRoaming}"
            )
        } else {
            Log.d("GameView", "checkInternetStatus: Not connected")
        }


        return isConnected
    }

}