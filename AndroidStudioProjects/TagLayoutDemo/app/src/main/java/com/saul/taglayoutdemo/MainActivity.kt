package com.saul.taglayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saul.taglayoutdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for(index in 0..10) {
            binding.tagLayout.addView(RandomTextView(this))
        }
    }
}