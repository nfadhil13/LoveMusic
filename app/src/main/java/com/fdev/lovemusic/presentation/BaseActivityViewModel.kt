package com.fdev.lovemusic.presentation

import com.fdev.lovemusic.util.SingleEvent

abstract class BaseActivityViewModel : BaseViewModel() {



    fun showErrorMessage(errorMessage : String){
        _errorMessage.value = SingleEvent(errorMessage)
    }


    fun startLoading(){
        _loading.value = true
    }

    fun finishLoading(){
        _loading.value = false
    }

}