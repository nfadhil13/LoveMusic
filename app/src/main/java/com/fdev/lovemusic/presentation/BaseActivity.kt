package com.fdev.lovemusic.presentation

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fdev.lovemusic.util.UIInteraction

abstract class BaseActivity : AppCompatActivity() {


    fun handleUIInteraction(interaction : UIInteraction){
        when(interaction){
            is UIInteraction.ShowToast -> {
                Toast.makeText(this , interaction.message , interaction.toasTime).show()
            }
            else -> {

            }
        }
    }

}