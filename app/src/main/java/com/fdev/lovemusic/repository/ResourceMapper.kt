package com.fdev.lovemusic.repository

import com.fdev.lovemusic.datasource.network.NetworkResource

fun <T> networkResourceToResource(networkResource: NetworkResource<T>) : Resource<T>{
    return when(networkResource){
        is NetworkResource.Success -> {
            Resource.Success(
                    data = networkResource.data,
                    onSuccessInteraction = networkResource.onSuccessInteraction
            )
        }
        is NetworkResource.Error -> {
            Resource.Error(
                    onErrorInteraction = networkResource.onErrorInteraction
            )
        }
    }
}