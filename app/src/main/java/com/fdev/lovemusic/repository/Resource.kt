package com.fdev.lovemusic.repository

import com.fdev.lovemusic.util.UserInteraction

sealed class Resource<T>{
    data class Success<T>(val data: T , val onSuccessInteraction : UserInteraction) : Resource<T>()
    class Loading<T>() : Resource<T>()
    data class Error<T>(val onErrorInteraction: UserInteraction) : Resource<T>()
}


