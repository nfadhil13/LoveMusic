package com.fdev.lovemusic.interactors

import com.fdev.lovemusic.datasource.DataSourceResource

fun <T> networkResourceToResource(networkResource: DataSourceResource<T>) : Resource<T>{
    return when(networkResource){
        is DataSourceResource.Success -> {
            println("Success")
            Resource.Success(
                    data = networkResource.data,
                    onSuccessInteraction = networkResource.onSuccessInteraction
            )
        }
        is DataSourceResource.Error -> {
            println("Error , ${networkResource.onErrorInteraction.message}")
            Resource.Error(
                    onErrorInteraction = networkResource.onErrorInteraction
            )
        }
    }
}