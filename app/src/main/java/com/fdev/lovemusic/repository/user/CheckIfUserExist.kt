package com.fdev.lovemusic.repository.user


import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.repository.Resource
import com.fdev.lovemusic.repository.networkResourceToResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckIfUserExist
@Inject
constructor(
        private val userNetworkDatasource: UserNetworkDatasource
){

    fun fetch(
            idToken : String,
            dispatcher: CoroutineDispatcher
    ): Flow<Resource<Boolean>> = flow{
        emit(networkResourceToResource(userNetworkDatasource.checkIfUserExist(idToken)))
    }.flowOn(dispatcher)

}