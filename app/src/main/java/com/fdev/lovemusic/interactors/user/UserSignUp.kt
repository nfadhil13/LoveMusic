package com.fdev.lovemusic.interactors.user

import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.interactors.Resource
import com.fdev.lovemusic.interactors.networkResourceToResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserSignUp
@Inject
constructor(
        private val userNetworkDatasource: UserNetworkDatasource
){


    fun googleSignUp(
            user : User,
            idToken : String,
            dispatcher: CoroutineDispatcher
    ) : Flow<Resource<User>> = flow{
        emit(networkResourceToResource(userNetworkDatasource.signUp(user, idToken)))
    }.flowOn(dispatcher)
}