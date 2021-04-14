package com.yourwebsite.guessthenumber

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yourwebsite.guessthenumber.databinding.ActivityGameViewBinding
import com.yourwebsite.guessthenumber.databinding.ActivityResultBinding
import kotlin.math.max

class activity_game_view : AppCompatActivity() {

    private lateinit var binding: ActivityGameViewBinding

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
                    //Show Winning Alert
                }
            }else{
                //Show Game over Alert
            }

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
                resetGame()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetGame(){
        generateRandomNumber()
        numberOfTries = 3
        binding.txtNumberOfTries.visibility = View.GONE
        binding.txtNumberChosen.visibility = View.GONE
        guessedNumber = ""
        binding.guessedNumberEditText.setText(guessedNumber)
        binding.txtNumberChosen.setTextColor(Color.RED)
    }

}