package com.fdev.lovemusic.interactors

import com.fdev.lovemusic.datasource.network.NetworkErrorConst
import com.fdev.lovemusic.util.UIInteraction
import kotlinx.coroutines.TimeoutCancellationException

fun errorHandler(exception: Exception , specifErrorHandler : ((exception : Exception) -> UIInteraction? )? = null) : UIInteraction {
    specifErrorHandler?.let{
        it(exception)?.let{
            return it
        }
    }
    var userInteraction : UIInteraction = exception.message?.let{
        UIInteraction.ShowToast(toastMessage = it)
    }?: UIInteraction.ShowToast(toastMessage = InteractorConst.UNKNOWN_ERROR)

    when(exception){
        is TimeoutCancellationException -> {
            userInteraction = UIInteraction.ShowToast(toastMessage = NetworkErrorConst.TIMEOUT_ERROR)
        }
    }
    return userInteraction
}
