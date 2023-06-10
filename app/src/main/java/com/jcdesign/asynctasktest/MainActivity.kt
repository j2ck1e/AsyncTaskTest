package com.jcdesign.asynctasktest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val myUrl= "https://www.google.ru/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task = DownLoadTask()
        try {
            val result = task.execute(myUrl).get()
            Log.i("MyTag", result)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    class DownLoadTask : AsyncTask<String, Unit, String>(){
        override fun doInBackground(vararg params: String?): String {
            val result = StringBuilder()
            lateinit var urlConnection : HttpURLConnection
            try {
                val url = URL(params[0])
                urlConnection = url.openConnection() as HttpURLConnection
                val inputStream = urlConnection.inputStream
                val reader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(reader)
                var line = bufferedReader.readLine()
                while(line != null){
                    result.append(line)
                    line = bufferedReader.readLine()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }

            return result.toString()
        }

    }
}