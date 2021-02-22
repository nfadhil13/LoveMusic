package com.fdev.lovemusic.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fdev.lovemusic.R
import com.fdev.lovemusic.databinding.ActivityAuthBinding
import com.fdev.lovemusic.databinding.ActivityMainBinding
import com.fdev.lovemusic.presentation.auth.AuthActivity
import com.fdev.lovemusic.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject lateinit var sessionManager: SessionManager

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.logout.setOnClickListener {
            sessionManager.logOut()
            navToAuthActivity()
        }
    }

    private fun navToAuthActivity(){
        val intent = Intent(this , AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}   