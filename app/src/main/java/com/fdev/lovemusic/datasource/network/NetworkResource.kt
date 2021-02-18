package com.fdev.lovemusic.datasource.network

import com.fdev.lovemusic.util.UserInteraction

sealed class NetworkResource<T>{
    data class Success<T>(val data: T,val onSuccessInteraction : UserInteraction) : NetworkResource<T>()
    data class Error<T>(val onErrorInteraction: UserInteraction) : NetworkResource<T>()
}


