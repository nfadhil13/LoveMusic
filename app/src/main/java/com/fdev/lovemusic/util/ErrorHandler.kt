package com.fdev.lovemusic.repository

import com.fdev.lovemusic.datasource.network.NetworkErrorConst
import com.fdev.lovemusic.util.UserInteraction
import kotlinx.coroutines.TimeoutCancellationException

fun errorHandler(exception: Exception , specifErrorHandler : ((exception : Exception) -> UserInteraction? )? = null) : UserInteraction {
    specifErrorHandler?.let{
        it(exception)?.let{
            return it
        }
    }
    var userInteraction : UserInteraction = exception.message?.let{
        UserInteraction.ShowToast(toastMessage = it)
    }?: UserInteraction.ShowToast(toastMessage = RepositoryConst.UNKNOWN_ERROR)

    when(exception){
        is TimeoutCancellationException -> {
            userInteraction = UserInteraction.ShowToast(toastMessage = NetworkErrorConst.TIMEOUT_ERROR)
        }
    }
    return userInteraction
}
