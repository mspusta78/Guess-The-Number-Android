package com.yourwebsite.guessthenumber.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.gson.JsonObject
import com.yourwebsite.guessthenumber.R
import com.yourwebsite.guessthenumber.databinding.ActivityTestBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class activity_test : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding
    var mOkHttpClient = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)

        binding.btnCallPost.setOnClickListener {
            if (checkInternetStatus()){
                postEcho()
            }
        }


    }

    private fun postEcho(){
        val postData = JsonObject()
        postData.addProperty("someDataField", "Test123")
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val postBody = RequestBody.create(JSON, postData.toString())

        val post = Request.Builder()
            .url("https://postman-echo.com/post")
            .post(postBody)
            .build()

        mOkHttpClient.newCall(post).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("error")
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string();
                Log.d("ActivityTest", "onResponse: response: $responseBody")
            }
        })
    }

    private fun checkInternetStatus():Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected) {
            Log.d(
                "MainActivity",
                "checkInternetStatus: Connected to the internet. Cellular Data: ${activeNetwork?.isRoaming}"
            )
        } else {
            Log.d("MainActivity", "checkInternetStatus: Not connected")
        }


        return isConnected
    }
}