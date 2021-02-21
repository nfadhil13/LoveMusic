package com.fdev.lovemusic.datasource.network

import com.fdev.lovemusic.datasource.DataSourceResource
import com.fdev.lovemusic.util.UIInteraction
import kotlinx.coroutines.TimeoutCancellationException

abstract class NetworkSaveCall<T> {

    protected abstract suspend fun networkCall() : DataSourceResource<T>


    protected open fun specificErrorHandler(exception: Exception): DataSourceResource.Error<T>?{
        return null
    }

    private fun onError(exception: Exception): DataSourceResource.Error<T> {
        println(exception)
        specificErrorHandler(exception)?.let {
            return it
        }
        var userInteraction: UIInteraction = exception.message?.let {
            UIInteraction.ShowToast(toastMessage = it)
        } ?: UIInteraction.ShowToast(toastMessage = NetworkErrorConst.UNKNOWN_ERROR)

        when (exception) {
            is TimeoutCancellationException -> {
                userInteraction = UIInteraction.ShowToast(toastMessage = NetworkErrorConst.TIMEOUT_ERROR)
            }
        }
        return DataSourceResource.Error(userInteraction)
    }

    suspend fun fetch(): DataSourceResource<T> {

        return try {
            networkCall()
        } catch (exception: Exception) {
            onError(exception)
        }
    }

}