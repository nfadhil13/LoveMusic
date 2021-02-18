//package com.fdev.lovemusic.repository
//
//import com.fdev.lovemusic.repository.Resource
//import com.fdev.lovemusic.util.UserInteraction
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//
//fun <T> saveCall(
//        tobeExecute: suspend () -> T,
//        dispatcher: CoroutineDispatcher,
//        specificErrorHandler: ((exception: Exception) -> UserInteraction?)?
//) : Flow<Resource<T>> = flow{
//    try{
//        emit(Resource.Loading<T>())
//        emit(Resource.Success(data = tobeExecute() , ))
//    }catch(exception : Exception){
//        emit(Resource.Error<T>(onErrorInteraction = errorHandler(exception = exception , specifErrorHandler = specificErrorHandler)))
//
//    }
//}.flowOn(dispatcher)