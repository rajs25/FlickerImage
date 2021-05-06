package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {

//            Extracting the search query from the textbox
            val message:String = searchText.text.toString()
            Log.d("Message :-",message)


            Log.d("Message :-","From main Activity")
            val intent = Intent(this,activity_image::class.java)
            intent.putExtra("message",message)
            startActivity(intent)

        }
    }
}