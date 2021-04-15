package com.yourwebsite.guessthenumber.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.yourwebsite.guessthenumber.HighScoreItem
import com.yourwebsite.guessthenumber.HighScoreResult
import com.yourwebsite.guessthenumber.R
import com.yourwebsite.guessthenumber.adapters.HighScoreItemAdapter
import com.yourwebsite.guessthenumber.databinding.ActivityHighScoreBinding
import okhttp3.*
import java.io.IOException
import java.util.ArrayList

class activity_high_score : AppCompatActivity() {

    private lateinit var binding: ActivityHighScoreBinding
    var mOkHttpClient = OkHttpClient()

    var playerName = ""
    var playerScore = 0

    private val highScores = ArrayList<HighScoreItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_high_score)

        binding.topBar.barTitle.text = "High Score"
        binding.topBar.backArrow.visibility = View.VISIBLE
        binding.topBar.backArrow.setOnClickListener {
            finish()
        }

        loadData()

    }

    private fun loadData(){
        val get = Request.Builder()
            .url("https://guessthenumber123.web.app/api/highscore")
            .get()
            .build()

        mOkHttpClient.newCall(get).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("error")
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string();
                if (response.code == 200) {
                    val gson = GsonBuilder().create()
                    val item = gson.fromJson(responseBody, HighScoreResult::class.java)
                    if (item.results.size > 0){
                        playerName = item.results[0].displayname.toString()
                        playerScore = item.results[0].score ?: 0

                        for (currentPosition in 0 until item.results.size) {
                            val item = item.results[currentPosition]
                            highScores.add(item)
                        }

                        runOnUiThread{
                            setUpHighScoreRecyclerView()
                        }
                    }
                }

            }
        })
    }

    private fun setUpHighScoreRecyclerView() {
        binding.recyclerViewHighScore.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerViewHighScore.adapter = HighScoreItemAdapter(
            highScores,
            this
        )
    }

}