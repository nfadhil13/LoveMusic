package com.fdev.lovemusic.util

import com.fdev.lovemusic.datasource.network.NetworkErrorConst
import com.fdev.lovemusic.repository.RepositoryConst
import kotlinx.coroutines.TimeoutCancellationException

fun errorHandler(exception: Exception) : String{
    var message = exception.message ?: RepositoryConst.UNKNOWN_ERROR
    when(exception){
        is TimeoutCancellationException -> {
            message = NetworkErrorConst.TIMEOUT_ERROR
        }
    }
    return message
}
