package com.xx.moviebuddy

import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = MapsFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.map_frame_layout, fragment)
            .commit()
    }
}

