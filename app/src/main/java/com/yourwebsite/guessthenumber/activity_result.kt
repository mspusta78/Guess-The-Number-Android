package com.yourwebsite.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yourwebsite.guessthenumber.databinding.ActivityQuestionBinding
import com.yourwebsite.guessthenumber.databinding.ActivityResultBinding

class activity_result : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)

        binding.topBar.barTitle.text = "Answer"

        getIncomingIntent()

    }

    private fun getIncomingIntent(){
        if (intent.hasExtra("choice")){
            val choice = intent.getBooleanExtra("choice", false)
            binding.txtChoice.text = choice.toString()
        }
    }

}