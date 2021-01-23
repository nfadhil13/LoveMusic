package com.fdev.lovemusic.repository

import com.fdev.lovemusic.util.errorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> saveCall(
        tobeExecute : suspend  () -> T,
        dispatcher: CoroutineDispatcher
) : Flow<Resource<T>> = flow{
    try{
        emit(Resource.Loading<T>())
        emit(Resource.Success(data = tobeExecute() , ))
    }catch(exception : Exception){
        emit(Resource.Error<T>(message = errorHandler(exception = exception)))

    }
}.flowOn(dispatcher)