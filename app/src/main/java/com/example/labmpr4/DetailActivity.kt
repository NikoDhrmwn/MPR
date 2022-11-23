package com.example.labmpr4

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labmpr4.databinding.ActivityDetailBinding
import com.example.labmpr4.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

private lateinit var binding: ActivityDetailBinding
private lateinit var getAnime: String
private val listFacts = ArrayList<String>()

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAnime = intent?.getStringExtra(EXTRA_ANIME).toString()
        val getAnimeImg = intent?.getStringExtra(EXTRA_ANIME_IMG).toString()
        Picasso.get().load(getAnimeImg).into(binding.ivAnime)

        getListFacts()
    }
    private fun getListFacts(){
        val client = AsyncHttpClient()
        val url = "https://anime-facts-rest-api.herokuapp.com/api/v1/"+getAnime
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>,
                                   responseBody: ByteArray)
            {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val data = responseObject.getString("data")
                    val jsonArray = JSONArray(data)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val fact = jsonObject.getString("fact")
                        listFacts.add(fact)
                    }
                    showRecycler()
                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>,
                                   responseBody: ByteArray, error: Throwable)
            {
                Toast.makeText(this@DetailActivity, statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRecycler(){
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.listAnime.layoutManager = GridLayoutManager(this, 2)
        }
        else{
            binding.listAnime.layoutManager = LinearLayoutManager(this)
        }

        val adapter = DetailAnimeAdapter(listFacts)
        binding.listAnime.adapter = adapter


    }
    companion object{
        const val EXTRA_ANIME = "extra_anime"
        const val EXTRA_ANIME_IMG = "extra_anime_img"
    }
}