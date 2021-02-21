package com.fdev.lovemusic.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.fdev.lovemusic.databinding.ActivityAuthBinding
import com.fdev.lovemusic.presentation.BaseActivity
import com.fdev.lovemusic.presentation.InteractorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var binding : ActivityAuthBinding
    private val interactorViewModel : InteractorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeVM()
    }

    private fun observeVM() {
        interactorViewModel.loading.observe(this , {
            toogleLoading(it)
        })

        interactorViewModel.userInteraction.observe(this , {
            val interaction = it.get() ?: return@observe
            handleUIInteraction(interaction)
        })
    }


    private fun toogleLoading(isLoading : Boolean){
        binding.apply {
            if(isLoading){
                overlay.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
            }else{
                overlay.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }

    }

}