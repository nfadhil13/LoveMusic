package com.fdev.lovemusic.interactors

import com.fdev.lovemusic.util.UIInteraction

sealed class Resource<T>{
    data class Success<T>(val data: T , val onSuccessInteraction : UIInteraction) : Resource<T>()
    class Loading<T>() : Resource<T>()
    data class Error<T>(val onErrorInteraction: UIInteraction) : Resource<T>()
}


