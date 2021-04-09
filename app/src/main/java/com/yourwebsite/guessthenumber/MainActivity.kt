package com.yourwebsite.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.yourwebsite.guessthenumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnPressMe.setOnClickListener {
            count += 1
            binding.btnPressMe.text = "I was Pressed $count times."
        }

        binding.btnPressMe.setOnLongClickListener {
            count += 1
            binding.btnPressMe.text = "I was Long Pressed $count times."
            true
        }

    }
}