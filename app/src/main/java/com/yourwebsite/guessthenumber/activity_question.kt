package com.yourwebsite.guessthenumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.yourwebsite.guessthenumber.databinding.ActivityHighScoreBinding
import com.yourwebsite.guessthenumber.databinding.ActivityQuestionBinding

class activity_question : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question)

        binding.topBar.barTitle.text = "Question"

        binding.btnTrue.setOnClickListener {
            val intent = Intent(this, activity_result::class.java)
            intent.putExtra("choice", true)
            startActivity(intent)
        }

        binding.btnFalse.setOnClickListener {
            val intent = Intent(this, activity_result::class.java)
            intent.putExtra("choice", false)
            startActivity(intent)
        }

    }
}