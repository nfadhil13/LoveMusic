package com.fdev.lovemusic.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.fdev.lovemusic.databinding.ActivityAuthBinding
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.presentation.BaseActivity
import com.fdev.lovemusic.presentation.ActivityBaseViewModel
import com.fdev.lovemusic.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var binding : ActivityAuthBinding
    private val activityViewModel : AuthActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeVM()
    }

    private fun observeVM() {
        activityViewModel.loading.observe(this , {
            toogleLoading(it)
        })

        activityViewModel.userInteraction.observe(this , {
            val interaction = it.get() ?: return@observe
            handleUIInteraction(interaction)
        })

        activityViewModel.sessionManager.currentUser.observe(this , { user ->
            println("Ada user baru lohhh : ${user}")
            if(user == null) return@observe
            navToMainActivity()
        })
    }

    private fun navToMainActivity() {
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun toogleLoading(isLoading : Boolean){
        binding.apply {
            if(isLoading){
                overlay.visibility = View.VISIBLE
            }else{
                overlay.visibility = View.GONE
            }
        }

    }

}