package com.fdev.lovemusic.repository.user

import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.repository.Resource
import com.fdev.lovemusic.repository.saveCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSignUp
@Inject
constructor(
        private val userNetworkDatasource: UserNetworkDatasource
){


    fun fetch(
            user : User,
            idToken : String,
            dispatcher: CoroutineDispatcher
    ) : Flow<Resource<User>> = saveCall(
            tobeExecute = {
                  userNetworkDatasource.signUp(user , idToken)
            },
            dispatcher = dispatcher
    )

}