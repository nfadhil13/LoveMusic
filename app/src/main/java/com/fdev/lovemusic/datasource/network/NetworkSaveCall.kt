package com.fdev.lovemusic.datasource.network

import com.fdev.lovemusic.repository.RepositoryConst
import com.fdev.lovemusic.util.UserInteraction
import kotlinx.coroutines.TimeoutCancellationException

abstract class NetworkSaveCall<T> {

    protected abstract suspend fun networkCall() : NetworkResource<T>


    protected open fun specificErrorHandler(exception: Exception): NetworkResource.Error<T>?{
        return null
    }

    private fun onError(exception: Exception): NetworkResource.Error<T> {
        specificErrorHandler(exception)?.let {
            return it
        }
        var userInteraction: UserInteraction = exception.message?.let {
            UserInteraction.ShowToast(toastMessage = it)
        } ?: UserInteraction.ShowToast(toastMessage = NetworkErrorConst.UNKNOWN_ERROR)

        when (exception) {
            is TimeoutCancellationException -> {
                userInteraction = UserInteraction.ShowToast(toastMessage = NetworkErrorConst.TIMEOUT_ERROR)
            }
        }
        return NetworkResource.Error(userInteraction)
    }

    suspend fun fetch(): NetworkResource<T> {

        return try {
            networkCall()
        } catch (exception: Exception) {
            onError(exception)
        }
    }

}