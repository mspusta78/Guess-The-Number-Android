package com.yourwebsite.guessthenumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yourwebsite.guessthenumber.databinding.ActivityMainBinding
import com.yourwebsite.guessthenumber.databinding.ActivitySplashBinding

class activity_splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.btnStart.setOnClickListener {
            val name = binding.nameEditText.text!!
            if (name.isBlank()){
                binding.nameEditText.error = "Enter Name"
            }else{
                //Your code here
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}