package com.fdev.lovemusic.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fdev.lovemusic.R
import com.fdev.lovemusic.databinding.ActivityAuthBinding
import com.google.firebase.ktx.initialize

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}