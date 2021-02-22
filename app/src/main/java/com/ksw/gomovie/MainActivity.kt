package com.ksw.gomovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ksw.gomovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val view = binding.root
        setContentView(view)

        binding.tvTest.text = "asdf"

    }
}