package com.fdev.lovemusic.datasource

import com.fdev.lovemusic.util.UIInteraction

sealed class DataSourceResource<T>{
    data class Success<T>(val data: T,val onSuccessInteraction : UIInteraction = UIInteraction.DoNothing) : DataSourceResource<T>()
    data class Error<T>(val onErrorInteraction: UIInteraction = UIInteraction.DoNothing) : DataSourceResource<T>()
}


