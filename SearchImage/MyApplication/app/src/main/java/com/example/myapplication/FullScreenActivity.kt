package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen.*
import kotlinx.android.synthetic.main.layout.view.*

class FullScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)
        val resultJson: String? = getIntent().getStringExtra("photo")
        val result : Photo = Gson().fromJson(resultJson,Photo::class.java)
        Picasso.get().load(result.url).into(fullscreen_image)
    }
}