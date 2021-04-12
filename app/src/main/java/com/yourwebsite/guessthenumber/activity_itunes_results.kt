package com.yourwebsite.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.yourwebsite.guessthenumber.adapters.ItunesItemAdapter
import com.yourwebsite.guessthenumber.databinding.ActivityItunesResultsBinding
import okhttp3.*
import java.io.IOException
import java.util.ArrayList

class activity_itunes_results : AppCompatActivity() {

    private lateinit var binding: ActivityItunesResultsBinding

    var mOkHttpClient = OkHttpClient()

    var albumUrl = ""
    var trackName = ""
    var artistName = ""

    private val albums = ArrayList<ItunesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_itunes_results)

        getItunesData()
    }

    private fun getItunesData() {
        //This function will call iTunes Search API
        val get = Request.Builder()
            .url("https://itunes.apple.com/search?term=elvis+presley&entity=musicVideo")
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
                    val item = gson.fromJson(responseBody, ItunesResult::class.java)
                    if (item.results.size > 0){
                        albumUrl = item.results[0].artworkUrl100.toString()
                        trackName = item.results[0].trackName.toString()
                        artistName = item.results[0].artistName.toString()

                        for (currentPosition in 0 until item.results.size) {
                            val item = item.results[currentPosition]
                            albums.add(item)
                        }

                        runOnUiThread{
                            setUpItunesRecyclerView()
                        }
                    }
                } else {
                    //Show error message
                    Log.e("ItunesResult", "Error getting data")
                }
            }
        })
    }

    private fun setUpItunesRecyclerView() {
        binding.recyclerViewItunes.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerViewItunes.adapter = ItunesItemAdapter(
            albums,
            this
        )
    }

}