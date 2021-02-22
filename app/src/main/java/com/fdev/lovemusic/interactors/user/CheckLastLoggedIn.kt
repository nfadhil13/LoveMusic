package com.fdev.lovemusic.interactors.user

import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.interactors.Resource
import com.fdev.lovemusic.interactors.networkResourceToResource
import com.fdev.lovemusic.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckLastLoggedIn
@Inject
constructor(
        private val userNetworkDatasource: UserNetworkDatasource
){


    fun fetch(
            dispatcher: CoroutineDispatcher
    ) : Flow<Resource<User?>> = flow{
        emit(networkResourceToResource(userNetworkDatasource.checkLastLoggedInUser()))
    }.flowOn(dispatcher)
}